package ru.javapft.soap;

import com.cdyne.ws.IP2Geo;
import com.cdyne.ws.IPInformation;
import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertEquals;

public class GeoIpTest {

  @Test
  public void testGeoIp() {
    IPInformation ip = new IP2Geo().getIP2GeoSoap().resolveIP("77.40.62.124", "test");
    assertEquals("RU", ip.getCountryCode());
  }
}
