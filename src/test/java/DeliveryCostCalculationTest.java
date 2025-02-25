import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import petrovsky.CargoDimension;
import petrovsky.Delivery;
import petrovsky.ServiceWorkload;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DeliveryCostCalculationTest {
    @Test
    @Tag("Example")
    @DisplayName("Example test")
    void testCheapestOrder() {
        Delivery delivery = new Delivery(1, CargoDimension.SMALL, false, ServiceWorkload.NORMAL);
        assertEquals(400, delivery.calculateDeliveryCost());
    }


    @ParameterizedTest
    @Tag("Positive")
    @DisplayName("Standard Order")
    @MethodSource("StandardOrderTestData")
    void testStandardOrder(CargoDimension dim, int dist, boolean fra, ServiceWorkload loa, int result){
        Delivery delivery = new Delivery(dist, dim, fra, loa);
        assertEquals(result, delivery.calculateDeliveryCost());
    }
    private static Stream<Arguments> StandardOrderTestData() {
        return Stream.of(
                Arguments.of(CargoDimension.SMALL, 0, true, ServiceWorkload.NORMAL, 450),   // +100,+50, +300, x1  expectedResult=450
                Arguments.of(CargoDimension.SMALL, 5, false, ServiceWorkload.HIGH, 400),   // +100,+100, +0, x1.4, expectedResult=400
                Arguments.of(CargoDimension.LARGE, 15, true, ServiceWorkload.VERY_HIGH, 1120),  // +200,+200, +300, x1.6, expectedResult=1120
                Arguments.of(CargoDimension.LARGE,200, false, ServiceWorkload.INCREASED, 600)  // +200,+300, +0, x1.2, expectedResult=600
        );
    }

    @ParameterizedTest
    @Tag("Positive")
    @DisplayName("FragileOrderPositive")
    @MethodSource("FragileOrderPositiveTestData")
    void testFragileOrderPositive(CargoDimension dim, int dist, boolean fra, ServiceWorkload loa, int result){
        Delivery delivery = new Delivery(dist, dim, fra, loa);
        assertEquals(result, delivery.calculateDeliveryCost());
    }
    private static Stream<Arguments> FragileOrderPositiveTestData() {
        return Stream.of(
                //    Arguments.of(CargoDimension.SMALL, 155, true, ServiceWorkload.NORMAL, 0),     // +100, +300, +300, x1  expectedResult=0
                Arguments.of(CargoDimension.SMALL, 29, true, ServiceWorkload.HIGH, 840),        // +100,+200, +300, x1.4, expectedResult=840
                Arguments.of(CargoDimension.LARGE, 30, true, ServiceWorkload.VERY_HIGH, 1120)  // +200,+300, +300, x1.6, expectedResult=1120
                //     Arguments.of(CargoDimension.LARGE,31, true, ServiceWorkload.INCREASED, 0)     // +200,+300, +300, x1.2, expectedResult=0
        );
    }

    @ParameterizedTest
    @Tag("Negative")
    @DisplayName("FragileOrderNegative")
    @MethodSource("FragileOrderNegativeTestData")
    void testFragileOrderNegative(CargoDimension dim, int dist, boolean fra, ServiceWorkload loa, String result){
        Delivery delivery = new Delivery(dist, dim, fra, loa);
        Throwable exception = assertThrows(
                UnsupportedOperationException.class,
                delivery::calculateDeliveryCost
        );
        assertEquals(result, exception.getMessage());
    }
    private static Stream<Arguments> FragileOrderNegativeTestData() {
        return Stream.of(
                    Arguments.of(CargoDimension.SMALL, 155, true, ServiceWorkload.NORMAL, "Fragile cargo cannot be delivered for the distance more than 30"),     // +100, +300, +300, x1  expectedResult=0
                    Arguments.of(CargoDimension.LARGE,31, true, ServiceWorkload.INCREASED, "Fragile cargo cannot be delivered for the distance more than 30")     // +200,+300, +300, x1.2, expectedResult=0
        );
    }

        @Test
        @Tag("Negative")
        @DisplayName("DistanceOrderNegative")
        void testDistanceOrderNegative() {
            Delivery delivery = new Delivery(-1, CargoDimension.SMALL, false, ServiceWorkload.NORMAL);
            Throwable exception = assertThrows(
                    IllegalArgumentException.class,
                    delivery::calculateDeliveryCost
            );
            assertEquals("destinationDistance should be a positive number!", exception.getMessage());
        }

}
