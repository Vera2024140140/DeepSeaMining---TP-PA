package pt.isec.pa.deepsea.model.data.elementos;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class ComponenteTest {
    @Test
    void testaIDsArtefacto(){
        Artefacto a1 = new Artefacto(5,5);
        Artefacto a2 = new Artefacto(2,3);
        assertEquals(1,a1.getId());
        assertEquals(2,a2.getId());
        assertNotEquals(a1.getId(),a2.getId());
    }
    @Test
    void testaIDsObstaculos(){
        Monstro m1  = new Monstro(1,1);
        Corrente c1 = new Corrente(1,2);
        AnimalMarinho a3 = new AnimalMarinho(1,3);

        assertEquals(1001,m1.getId());
        assertEquals(1002,c1.getId());
        assertEquals(1003,a3.getId());
    }
}
