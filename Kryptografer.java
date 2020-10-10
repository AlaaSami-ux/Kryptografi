/*Kryptograftrådene sin oppgave er å motta meldinger fra monitoren for krypterte meldinger.
Kryptografen skal så dekryptere meldingen og så sende den ferdig dekrypterte meldingen
videre til monitoren for dekrypterte meldinger. Merk at kryptografene ikke trenger å ta
hensyn til sekvensnummer eller kanal-ID når de henter ut meldingene.*/

class Kryptografer implements Runnable{
  Monitor monitor;
  D_KryptMonitor dekrypt;
  Kryptografi krypto;
  Melding meld;
  Kanal kanal;

  public Kryptografer(Monitor monitor, D_KryptMonitor dekrypt){
    this.monitor = monitor;
    this.dekrypt = dekrypt;
  }

  @Override
  public void run(){
    Melding krypMel = monitor.hentMelding();
    // System.out.println(krypMel);
    // Melding meld = new Melding(melding,sek,kan);
    while(krypMel != null){
      String melding = krypto.dekrypter(krypMel.hentInnhold());
      // System.out.println(melding);
      krypMel.settInnhold(melding);
      dekrypt.leggTilDeKrypterrtMelding(krypMel);
      krypMel = monitor.hentMelding();
    }
    // System.out.println("Kripto er ferdig" );
    dekrypt.telgErFerdig();

  }
}
