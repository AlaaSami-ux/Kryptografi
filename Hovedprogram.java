/*I hovedprogrammet ditt skal du opprette en Operasjonssentral, monitorene, telegrafister,
kryptografer og operasjonslederen. Deretter må telegrafistene, kryptografene og
operasjonslederen settes i arbeid. Hvor mange telegrafister og kryptografer du har, spiller
ingen rolle, så lenge det er et positivt tall, men husk at det skal være like mange telegrafister
som det er kanaler (hver telegrafist har sin egen kanal).*/

public class Hovedprogram{

  public static void main(String[] args){

    int antallTelegrafister =3;
    int antalKryptografer = 20;
    Operasjonssentral ops = new Operasjonssentral(antallTelegrafister);
    Kanal[] kanaler = ops.hentKanalArray();
    Monitor mon = new Monitor(antallTelegrafister);
    D_KryptMonitor d_kryptMonitor = new D_KryptMonitor(antalKryptografer);
    Operasjonslederen operasjon = new Operasjonslederen(d_kryptMonitor);
    Runnable runable;

    for (int i = 0 ;i < antallTelegrafister; i++ ) {
      runable = new Telegrafist(kanaler[i], mon);
      new Thread(runable).start();
    }
    try{
      Thread.sleep(1000);
    }catch(InterruptedException e){}
    System.out.println(mon.beholder.size());
    for (int i = 0 ;i < antalKryptografer ; i++ ) {
      runable = new Kryptografer(mon, d_kryptMonitor);
      new Thread(runable).start();
    }
    try{
      Thread.sleep(1000);
    }catch(InterruptedException e){}
    System.out.println(d_kryptMonitor.beholder.size());
    runable = new Operasjonslederen(d_kryptMonitor);
    new Thread(runable).start();
  }
}
