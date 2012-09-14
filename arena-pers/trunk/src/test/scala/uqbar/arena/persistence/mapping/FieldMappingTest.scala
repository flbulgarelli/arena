package uqbar.arena.persistence.mapping

import org.junit.Assert
import org.junit.Test
import uqbar.arena.persistence.AbstractArenaPersistenceTest
import uqbar.arena.persistence.Configuration
import uqbar.arena.persistence.testDomain.Celular
import uqbar.arena.persistence.testDomain.Modelo
import uqbar.arena.persistence.testDomain.Persona
import java.util.Date


class FieldMappingTest extends AbstractArenaPersistenceTest {

  @Test
  def testCelular {
    var cel = new Celular(Modelo.MOTOROLA_1100, "12345", null, 1.25);
    var celMapping = Configuration.mappingFor(cel);

    Assert.assertNotNull(celMapping);

    var n = graphDb.createNode();
    for (m <- celMapping.getAllFields()) {
      m.persist(null,n, cel);
    }

    var newCel = new Celular();

    for (m <- celMapping.getAllFields()) {
      m.hidrate(null,n, newCel);
    }

    Assert.assertNotSame(cel, newCel);
    Assert.assertEquals(cel, newCel);
  }

  @Test
  def testPersona {
    var per = new Persona("Juan", "Perez", new Date(),37);
    var perMapping = Configuration.mappingFor(per);

    Assert.assertNotNull(perMapping);

    var n = graphDb.createNode();
    for (m <- perMapping.getAllFields()) {
      m.persist(null, n, per);
    }

    var newPer = new Persona()

    for (m <- perMapping.getAllFields()) {
      m.hidrate(null, n, newPer);
    }

    Assert.assertNotSame(per, newPer);
    Assert.assertEquals(per, newPer);
  }
}