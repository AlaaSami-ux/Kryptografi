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
class D_KryptMonitor{
  Lock laas = new ReentrantLock();
  Condition ikkeTom = laas.newCondition();
  LinkedList<Melding> beholder = new LinkedList<Melding>();
  int tellKriptografer = 0;
  int totKryptograf = 0;

  public D_KryptMonitor(int antKryp){
    totKryptograf = antKryp;

  }

    public void telgErFerdig(){
      laas.lock();
      try{
        tellKriptografer ++;
        if (tellKriptografer == totKryptograf) {
          ikkeTom.signalAll();
        }
      }
      finally{
        laas.unlock();
      }
    }
    public void leggTilDeKrypterrtMelding(Melding mel){
      laas.lock();
      try{
          beholder.add(mel);
          ikkeTom.signal();
      }
      finally{
        laas.unlock();
      }
    }

  public Melding hentDekryptertMelding(){
    laas.lock();
    try{
      while (beholder.size() == 0 && tellKriptografer != totKryptograf) {
        try{ikkeTom.await();}
        catch(InterruptedException e){}
        }
        //System.out.println(tellKriptografer +"|" + totKryptograf);
        if (tellKriptografer == totKryptograf) {
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
