package ohtu.ohtuvarasto;

import org.junit.*;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class VarastoTest {

    Varasto varasto;
    double vertailuTarkkuus = 0.0001;

    @Before
    public void setUp() {
        varasto = new Varasto(10);
    }

    @Test
    public void konstruktoriLuoTyhjanVaraston() {
        assertEquals(0, varasto.getSaldo(), vertailuTarkkuus);
    }

    @Test
    public void uudellaVarastollaOikeaTilavuus() {
        assertEquals(10, varasto.getTilavuus(), vertailuTarkkuus);
    }

    @Test
    public void lisaysLisaaSaldoa() {
        varasto.lisaaVarastoon(8);

        // saldon pitäisi olla sama kun lisätty määrä
        assertEquals(8, varasto.getSaldo(), vertailuTarkkuus);
    }

    @Test
    public void lisaysLisaaPienentaaVapaataTilaa() {
        varasto.lisaaVarastoon(8);

        // vapaata tilaa pitäisi vielä olla tilavuus-lisättävä määrä eli 2
        assertEquals(2, varasto.paljonkoMahtuu(), vertailuTarkkuus);
    }

    @Test
    public void ottaminenPalauttaaOikeanMaaran() {
        varasto.lisaaVarastoon(8);

        double saatuMaara = varasto.otaVarastosta(2);

        assertEquals(2, saatuMaara, vertailuTarkkuus);
    }

    @Test
    public void ottaminenLisaaTilaa() {
        varasto.lisaaVarastoon(8);

        varasto.otaVarastosta(2);

        // varastossa pitäisi olla tilaa 10 - 8 + 2 eli 4
        assertEquals(4, varasto.paljonkoMahtuu(), vertailuTarkkuus);
    }

    // merkkijonoesityksen testaus
    @Test
    public void merkkijonoesitysOikeanmuotoinen() {
        varasto = new Varasto(5.5, 4);
        assertEquals("saldo = 4.0, vielä tilaa 1.5", varasto.toString());
    }

    // negatiiviset lisäykset ja ottamiset
    @Test
    public void negatiivinenLisaysEiMuutaSaldoa() {
        varasto.lisaaVarastoon(8);
        varasto.lisaaVarastoon(-1);
        assertEquals(8, varasto.getSaldo(), vertailuTarkkuus);
    }

    @Test
    public void negatiivinenLisaysEiMuutaVapaataTilaa() {
        varasto.lisaaVarastoon(8);
        varasto.lisaaVarastoon(-1);
        assertEquals(2, varasto.paljonkoMahtuu(), vertailuTarkkuus);
    }

    @Test
    public void negatiivinenOttaminenEiMuutaSaldoa() {
        varasto.lisaaVarastoon(8);
        varasto.otaVarastosta(-1);
        assertEquals(8, varasto.getSaldo(), vertailuTarkkuus);
    }

    @Test
    public void negatiivinenOttaminenEiMuutaVapaataTilaa() {
        varasto.lisaaVarastoon(8);
        varasto.otaVarastosta(-1);
        assertEquals(2, varasto.paljonkoMahtuu(), vertailuTarkkuus);
    }

    // varasto on täyttynyt tai tyhjentynyt
    @Test
    public void tayteenLisayksenJalkeenEiMahduEnaaMitaan() {
        varasto.lisaaVarastoon(10);
        assertEquals(0, varasto.paljonkoMahtuu(), vertailuTarkkuus);
    }

    @Test
    public void ylimaaraMeneeHukkaanKunLisataanEnemmanKuinTilavuus() {
        varasto.lisaaVarastoon(11);
        assertEquals(10, varasto.getSaldo(), vertailuTarkkuus);
        assertEquals(0, varasto.paljonkoMahtuu(), vertailuTarkkuus);
    }

    @Test
    public void ylimaaraMeneeHukkaanTayttymisenJalkeen() {
        varasto.lisaaVarastoon(8);
        varasto.lisaaVarastoon(2.2);
        assertEquals(10, varasto.getSaldo(), vertailuTarkkuus);
        assertEquals(0, varasto.paljonkoMahtuu(), vertailuTarkkuus);
    }

    @Test
    public void varastoTyhjentyyOikeinKunOtetaanSaldonMaara() {
        varasto.lisaaVarastoon(8);
        varasto.otaVarastosta(8);
        assertEquals(0, varasto.getSaldo(), vertailuTarkkuus);
        assertEquals(10, varasto.paljonkoMahtuu(), vertailuTarkkuus);
    }

    @Test
    public void varastoTyhjentyyOikeinKunOtetaanEnemmanKuinSaldonMaara() {
        varasto.lisaaVarastoon(8);
        varasto.otaVarastosta(9);
        assertEquals(0, varasto.getSaldo(), vertailuTarkkuus);
        assertEquals(10, varasto.paljonkoMahtuu(), vertailuTarkkuus);
    }

    @Test
    public void kayttokelvotonVarastoJosAnnettuTilavuusOnNolla() {
        varasto = new Varasto(0.0);
        assertEquals(0.0, varasto.getTilavuus(), vertailuTarkkuus);
    }

    @Test
    public void kayttokelvotonVarastoJosAnnettuTilavuusOnNegatiivinen() {
        varasto = new Varasto(-1);
        assertEquals(0.0, varasto.getTilavuus(), vertailuTarkkuus);
    }

    // Alkusaldoversion testaus
    @Test
    public void alkusaldovarastollaOikeaTilavuus() {
        varasto = new Varasto(5.5, 4);
        assertEquals(5.5, varasto.getTilavuus(), vertailuTarkkuus);
    }

    @Test
    public void kayttokelvotonAlkusaldovarastoJosNollaTilavuus() {
        varasto = new Varasto(0.0, 4);
        assertEquals(0.0, varasto.getTilavuus(), vertailuTarkkuus);
    }

    @Test
    public void kayttokelvotonAlkusaldovarastoJosNegatiivinenTilavuus() {
        varasto = new Varasto(-1, 4);
        assertEquals(0.0, varasto.getTilavuus(), vertailuTarkkuus);
    }

    @Test
    public void alkusaldovarastollaOikeaSaldo() {
        varasto = new Varasto(5.5, 4);
        assertEquals(4, varasto.getSaldo(), vertailuTarkkuus);
    }

    @Test
    public void alkusaldovarastoTyhjaJosNegatiivinenAlkusaldo() {
        varasto = new Varasto(5.5, -1);
        assertEquals(0, varasto.getSaldo(), vertailuTarkkuus);
    }

    @Test
    public void alkusaldovarastoonOtetaanVainTilavuudenVerran() {
        varasto = new Varasto(5.5, 5.6);
        assertEquals(5.5, varasto.getSaldo(), vertailuTarkkuus);
    }
}
