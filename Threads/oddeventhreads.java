

class sresource 
{
    static int num=0;
    static boolean gennum;
}


class oddthread extends Thread
{
    sresource s;
    public oddthread(sresource s)
    {
        this.s = s;
    }
    public void run()
    {
        int n=0;
        synchronized (s)
        {
            while(sresource.num<=100)
            {
                while(sresource.gennum)
                {
                    try
                    {
                        s.wait();
                    }
                    catch(InterruptedException r)
                    {
                        System.out.println("Exceprion");
                    }
                }
                n = getnum();
                if(n%2!=0 && n<=100)
                {
                    System.out.println("odd: "+n);
                    sresource.gennum=true;
                    s.notify();
                }
            }
        }
    }
    
        /* 
        try
        {
            Thread.sleep(1000);
        }
        catch(InterruptedException r)
        {
            System.out.println();
        }
        */

    private synchronized int getnum()
    {
        return sresource.num++;
    }
}
    



class eventhread extends Thread
{
    sresource s;
    public eventhread(sresource s)
    {
        this.s = s;
    }
    public void run()
    {
        synchronized (s)
        {
            while(sresource.num<=100)
            {
                while(!sresource.gennum)
                {
                    try
                    {
                        s.wait();
                    }
                    catch(InterruptedException r)
                    {
                        System.out.println("Exception");
                    }
                }

                int n = getnum();
                if(n%2==0 && n<=100)
                {
                    System.out.println("even :"+n);
                    sresource.gennum=false;
                    s.notify();
                }
            }
        }   
    }

    private synchronized int getnum()
    {
        return sresource.num++;
    }
    
}

class oddeventhreads
{
    public static void main(String[] args) 
    {
        sresource s = new sresource();
        sresource.gennum=false;
        oddthread o = new oddthread(s);
        eventhread e = new eventhread(s);
        o.start();
        e.start();
        
        try
        {
            o.join();
            e.join();
        }
        catch(Exception q)
        {
            System.out.println("Exception!");
        }
    }
}