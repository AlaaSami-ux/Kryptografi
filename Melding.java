/*Melding
I tillegg til String-objektet som utgjør innholdet i meldingen, må hver melding ha et
sekvensnummer og ID-en til kanalen meldingen kom fra. Denne informasjonen er nødvendig
for å kunne skille meldingene etter kanal og sortere dem i riktig rekkefølge slik at meldingene
fra hver kanal kan skrives ut sammen.
*/
public class Melding implements Comparable<Melding>{
  String innhold;
  int sekvensNummer;
  int kanalId;
  public Melding(String innhold, int sekvensNummer, int kanalId){
    this.innhold = innhold;
    this.sekvensNummer = sekvensNummer;
    this.kanalId = kanalId;
  }
  public int hentSekvensNummer(){return sekvensNummer;}
  public int hentKanalId(){return kanalId;}
  public String hentInnhold(){return innhold;}

  public void settInnhold(String innhold){
    this.innhold = innhold;
  }
  public String toString(){
    return innhold;
  }

  public int compareTo(Melding meld){
    return this.sekvensNummer - meld.hentSekvensNummer();
    // return Integer.valueOf(this.sekvensNummer);
  }
}
