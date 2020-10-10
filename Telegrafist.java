/*Telegrafister
Hver telegrafist(-tråd) har sin egen kanal. Telegrafistens oppgave er å lytte etter beskjeder
(husk at .lytt() returnerer en String) på kanalen sin. Når en telegrafist får en beskjed skal
den opprette en Melding og så sende meldingen videre til monitoren for krypterte meldinger,
for deretter å gå tilbake til å lytte etter nye meldinger. Når det ikke er flere meldinger å hente,
returnerer .lytt()-metoden “null*/

class Telegrafist implements Runnable{
  Kanal kanal;
  Monitor monitor;

  public Telegrafist(Kanal kanal , Monitor monitor){
    this.kanal = kanal;
    this.monitor = monitor;
  }

  @Override
  public void run(){
    String melding = kanal.lytt();
    int sek = 1;
    int kan = kanal.hentId();
    // System.out.println(melding);
    while(melding != null){
      Melding mel = new Melding(melding, sek, kan);
      monitor.leggTilMelding(mel);
      sek++;
      melding = kanal.lytt();
    }
    // System.out.println("teligrafis er ferdig ");
    monitor.telgErFerdig();
  }
  // public String hentMelding(int kanalId){
  //   for (int i = 0; i < tekst.tekster[kanalId].length; i++) {
  //     for (int j = 0 ; j < tekst.tekster[kanalId].length; j++) {
  //       m.sekvensNummer++;
  //       // System.out.println(tekst.tekster[kanalId].length);
  //       // System.out.println(tekst.tekster[kanalId][j] + "--------" +  sekvensNummer);
  //       m.innhold = tekst.tekster[kanalId][j] + "   --------   SEK NUMMER " +  m.sekvensNummer  + ".  KANAL ID : " + kanalId;
  //       this.m = new Melding(m.innhold,m.sekvensNummer,kanalId);
  //       System.out.println(this.m);
  //     }
  //     break;
  //   }
  //   return m.innhold + kanalId;
  // }

}
