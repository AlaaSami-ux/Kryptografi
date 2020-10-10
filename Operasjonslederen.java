/*Når alle telegrafistene og kryptografene er ferdige, skal operasjonslederen skrive
meldingene til fil, med en fil for hver kanal. For å kunne sortere meldingene kan det være
nyttig å legge til noe i Melding-klassen. Det kan også være lurt at operasjonslederen har en
(eller flere) beholdere for å holde styr på og sortere meldingene. Når meldingene skal
skrives til fil, skal hver melding skal være adskilt av to linjeskift. Pass på at meldingene
kommer i riktig rekkefølge!*/
import java.util.*;
import java.io.*;
// import java.util.ArrayList;
// import java.util.HashMap;
public class Operasjonslederen implements Runnable {
  D_KryptMonitor d_kryptMonitor;
  Operasjonssentral opreasjon;
  ArrayList<Melding> beholder = new ArrayList<Melding>();
  String fil = "kanal";
  Map<Integer,List<Melding>> allMeldinger = new HashMap<Integer,List<Melding>>();

  public Operasjonslederen(D_KryptMonitor d_kryptMonitor){
    this.d_kryptMonitor = d_kryptMonitor;
  }

  // public void hentMelding(){
  //   Melding m = d_kryptMonitor.hentDekryptertMelding();
  //   beholder.add(m);
  //   for (Melding meld : beholder){
  //     Integer kanalId = Integer.valueOf(meld.hentKanalId());
  //     if(!allMeldinger.containsKey(kanalId)){
  //       allMeldinger.put(kanalId, new ArrayList<Melding>());
  //     }
  //     allMeldinger.get(kanalId).add(meld);
  //   }
  // }
  public void settOgSorterMelding(){
    for (List<Melding> kanalen : allMeldinger.values()) {
      Collections.sort(kanalen);
      // System.out.println(kanalen.size());
    }
  }

  public void skrivTilFil(String fil){
    try{
      int i = 0;
      for (Map.Entry<Integer,List<Melding>> entry : allMeldinger.entrySet()){
        File f = new File(fil + i + ".txt");
        i++;
        PrintWriter w = new PrintWriter(f);
        for (Melding melding : entry.getValue()) {
          w.append(melding.toString()+"\n\n");
        }
        w.close();
      }
    } catch(Exception e){
      System.out.println(e.getMessage());
    }
  }
  @Override
  public void run(){
    Melding m = d_kryptMonitor.hentDekryptertMelding();
    while (m != null){
    beholder.add(m);
    Integer kanalId = Integer.valueOf(m.hentKanalId());
    if(!allMeldinger.containsKey(kanalId)){
      allMeldinger.put(m.kanalId, new ArrayList<Melding>());
      allMeldinger.get(kanalId).add(m);
    }else{
      allMeldinger.get(kanalId).add(m);
    }
    m = d_kryptMonitor.hentDekryptertMelding();
  }
    settOgSorterMelding();
    skrivTilFil(fil);
  }
}
