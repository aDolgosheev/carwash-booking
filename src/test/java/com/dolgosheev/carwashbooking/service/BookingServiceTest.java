package com.dolgosheev.carwashbooking.service;

import com.dolgosheev.carwashbooking.entity.Booking;
import com.dolgosheev.carwashbooking.entity.BookingStatus;
import com.dolgosheev.carwashbooking.entity.User;
import com.dolgosheev.carwashbooking.repository.BookingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookingServiceTest {

    @Mock
    private BookingRepository bookingRepository;

    @InjectMocks
    private BookingService bookingService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateBooking() {
        Booking booking = new Booking();
        booking.setBookingTime(LocalDateTime.now().plusDays(1));
        booking.setPrepayment(true);
        // Исходное значение status может быть null

        Booking savedBooking = new Booking(1L, booking.getUser(), booking.getService(),
                booking.getBookingTime(), booking.getPrepayment(), BookingStatus.BOOKED);
        when(bookingRepository.save(any(Booking.class))).thenReturn(savedBooking);

        Booking result = bookingService.createBooking(booking);

        assertNotNull(result);
        assertEquals(BookingStatus.BOOKED, result.getStatus());
        verify(bookingRepository).save(booking);
    }

    @Test
    void testGetBookingsByUser() {
        User user = new User();
        user.setId(1L);
        Booking booking1 = new Booking(1L, user, null, LocalDateTime.now().plusDays(1), false, BookingStatus.BOOKED);
        Booking booking2 = new Booking(2L, user, null, LocalDateTime.now().plusDays(2), true, BookingStatus.BOOKED);
        List<Booking> bookingList = Arrays.asList(booking1, booking2);
        when(bookingRepository.findByUser(user)).thenReturn(bookingList);

        List<Booking> result = bookingService.getBookingsByUser(user);

        assertEquals(2, result.size());
        verify(bookingRepository).findByUser(user);
    }

    @Test
    void testCancelBooking_whenBookingNotFound() {
        when(bookingRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> bookingService.cancelBooking(1L));
        assertEquals("Бронирование не найдено", exception.getMessage());
    }

    @Test
    void testCancelBooking_whenCancellationWithin3Hours() {
        // Если время бронирования меньше чем через 3 часа после вычитания 3 часов -> условие выполняется
        LocalDateTime bookingTime = LocalDateTime.now().plusHours(2); // bookingTime - 3h = now - 1h (до настоящего момента)
        Booking booking = new Booking(1L, null, null, bookingTime, true, BookingStatus.BOOKED);
        when(bookingRepository.findById(1L)).thenReturn(Optional.of(booking));
        when(bookingRepository.save(booking)).thenReturn(booking);

        Booking result = bookingService.cancelBooking(1L);

        assertEquals(BookingStatus.CANCELLED, result.getStatus());
        verify(bookingRepository).save(booking);
    }

    @Test
    void testCancelBooking_whenCancellationNotWithin3Hours() {
        // Если время бронирования достаточно далеко: bookingTime - 3h > now
        LocalDateTime bookingTime = LocalDateTime.now().plusHours(4); // bookingTime - 3h = now + 1h (после настоящего момента)
        Booking booking = new Booking(2L, null, null, bookingTime, true, BookingStatus.BOOKED);
        when(bookingRepository.findById(2L)).thenReturn(Optional.of(booking));
        when(bookingRepository.save(booking)).thenReturn(booking);

        Booking result = bookingService.cancelBooking(2L);

        // Статус останется BOOKED, так как условие отмены не выполняется
        assertEquals(BookingStatus.BOOKED, result.getStatus());
        verify(bookingRepository).save(booking);
    }
}
