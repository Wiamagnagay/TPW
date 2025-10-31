package ticketmachine;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TicketMachineTest {
    private static final int PRICE = 50; // Une constante
    private TicketMachine machine; // l'objet à tester
     
    @BeforeEach
    public void setUp() {
        machine = new TicketMachine(PRICE); // On initialise l'objet à tester
    }

    @Test
    // S1 : le prix affiché correspond à l'initialisation
    void priceIsCorrectlyInitialized() {
        assertEquals(PRICE, machine.getPrice(), "Initialisation incorrecte du prix");
    }

    @Test
    // S2 : la balance change quand on insère de l'argent
    void insertMoneyChangesBalance() {
        // GIVEN : une machine vierge (initialisée dans @BeforeEach)
        // WHEN : On insère de l'argent
        machine.insertMoney(10);
        machine.insertMoney(20);
        // THEN : La balance est mise à jour, les montants sont correctement additionnés
        assertEquals(30, machine.getBalance(), "La balance n'est pas correctement mise à jour");
    }

    @Test
    // S3 : on n'imprime pas le ticket si le montant inséré est insuffisant
    void noTicketWhenInsufficientBalance() {
        // GIVEN : une machine avec un montant insuffisant
        machine.insertMoney(PRICE - 1);
        // WHEN : On tente d'imprimer un ticket
        boolean result = machine.printTicket();
        // THEN : Le ticket n'est pas imprimé
        assertFalse(result, "Le ticket ne devrait pas être imprimé avec un montant insuffisant");
    }

    @Test
    // S4 : on imprime le ticket si le montant inséré est suffisant
    void ticketPrintedWhenSufficientBalance() {
        // GIVEN : une machine avec un montant suffisant
        machine.insertMoney(PRICE);
        // WHEN : On imprime un ticket
        boolean result = machine.printTicket();
        // THEN : Le ticket est imprimé
        assertTrue(result, "Le ticket devrait être imprimé avec un montant suffisant");
    }

    @Test
    // S5 : Quand on imprime un ticket la balance est décrémentée du prix du ticket
    void balanceDecreasesAfterPrintingTicket() {
        // GIVEN : une machine avec un montant suffisant
        machine.insertMoney(PRICE + 20);
        // WHEN : On imprime un ticket
        machine.printTicket();
        // THEN : La balance est décrémentée du prix du ticket
        assertEquals(20, machine.getBalance(), "La balance devrait être décrémentée du prix du ticket");
    }

    @Test
    // S6 : le montant collecté est mis à jour quand on imprime un ticket (pas avant)
    void totalCollectedUpdatedOnlyAfterPrinting() {
        // GIVEN : une machine vierge
        int initialTotal = machine.getTotal();
        // WHEN : On insère de l'argent sans imprimer
        machine.insertMoney(PRICE);
        // THEN : Le total collecté n'a pas changé
        assertEquals(initialTotal, machine.getTotal(), "Le total ne devrait pas changer avant impression");
        
        // WHEN : On imprime un ticket
        machine.printTicket();
        // THEN : Le total collecté est mis à jour
        assertEquals(initialTotal + PRICE, machine.getTotal(), "Le total devrait être mis à jour après impression");
    }

    @Test
    // S7 : refund() rend correctement la monnaie
    void refundReturnsCorrectAmount() {
        // GIVEN : une machine avec de l'argent inséré
        int insertedAmount = 30;
        machine.insertMoney(insertedAmount);
        // WHEN : On demande un remboursement
        int refunded = machine.refund();
        // THEN : Le montant retourné correspond au montant inséré
        assertEquals(insertedAmount, refunded, "Le montant remboursé devrait correspondre à la balance");
    }

    @Test
    // S8 : refund() remet la balance à zéro
    void refundResetsBalance() {
        // GIVEN : une machine avec de l'argent inséré
        machine.insertMoney(30);
        // WHEN : On demande un remboursement
        machine.refund();
        // THEN : La balance est remise à zéro
        assertEquals(0, machine.getBalance(), "La balance devrait être remise à zéro après remboursement");
    }

    @Test
    // S9 : on ne peut pas insérer un montant négatif
    void cannotInsertNegativeAmount() {
        // GIVEN : une machine vierge
        // WHEN : On tente d'insérer un montant négatif
        // THEN : Une exception est levée
        assertThrows(IllegalArgumentException.class, 
            () -> machine.insertMoney(-10),
            "Insérer un montant négatif devrait lever une exception");
    }
  //test
    @Test
    // S10 : on ne peut pas créer de machine qui délivre des tickets dont le prix est négatif
    void cannotCreateMachineWithNegativePrice() {
        // WHEN : On tente de créer une machine avec un prix négatif
        // THEN : Une exception est levée
        assertThrows(IllegalArgumentException.class,
            () -> new TicketMachine(-50),
            "Créer une machine avec un prix négatif devrait lever une exception");
    }
}