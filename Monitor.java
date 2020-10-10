/*Monitorer
Vi skal ha to monitorer i dette systemet, én monitor som mottar krypterte meldinger og
sender meldingene videre til kryptografer, og én monitor som tar i mot dekrypterte meldinger
og sender meldingene videre til operasjonsleder når alle meldinger er ferdig dekryptert. For
å holde på meldingene i monitoren bør du bruke en passende beholder, for eksempel
ArrayList eller LinkedList. Alternativt kan du benytte en beholder fra oblig 3 (vi vil oppfordre
til å benytte ArrayList eller LinkedList, da det kan være nyttig å bli bedre kjent med
Java-biblioteket). Pass på at ingen meldinger blir liggende for lenge i monitoren før de blir
hentet av en kryptograf.
Husk å synkronisere trådene dine, med andre ord: når en tråd har fått tilgang til en monitor,
må alle andre trådene vente til den første tråden er ferdig.
*/
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;
import java.util.LinkedList;

// LinkedList<String> beholder;
class Monitor{
  Lock laas = new ReentrantLock();
  Condition ikkeTom = laas.newCondition();
  LinkedList<Melding> beholder = new LinkedList<Melding>();
  int tellTilgrafister = 0;
  int totTilgraf = 0;

  public Monitor(int totTilgr){
    totTilgraf = totTilgr;
  }

  public void telgErFerdig(){
    laas.lock();
    try{
      tellTilgrafister ++;
      if (tellTilgrafister == totTilgraf) {
        ikkeTom.signalAll();
      }
    }
    finally{
      laas.unlock();
    }
  }

  public void leggTilMelding(Melding mel){
    laas.lock();
    try{
      beholder.add(mel);
      ikkeTom.signal();
    }
    finally{
      laas.unlock();
    }
  }

  public Melding hentMelding(){
    // Melding.hentMelding();
    laas.lock();
    try{
      // System.out.println(tellTilgrafister + "|" + totTilgraf);
      while (beholder.size() == 0 && tellTilgrafister != totTilgraf) {
        try{ikkeTom.await();}
        catch(InterruptedException e){}
      }
      if (tellTilgrafister == totTilgraf) {
        if (beholder.size() > 0 ) {
          return beholder.pop();
        }
        return null;
      }
      return beholder.pop();
    }
    finally{
      laas.unlock();
    }
  }
}
