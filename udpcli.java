import java.net.*;
import java.nio.ByteBuffer;
import java.util.*;

public class udpcli{
    
    

    public static void main(String [] args){

        try {
            if (args.length!=2) throw new Exception("Wrong number of parameters, correct use: \njava udpcli ip_address port_numer");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(-1);
        }
        
        int port=Integer.parseInt(args[1]); 
        Scanner imput = new Scanner(System.in);
        InetAddress address = null;
        DatagramSocket socket = null;

        try {
            
            address = InetAddress.getByName(args[0]);

        }

        catch(Exception e){
            System.out.println("Error getting address");
            System.exit(-1);
        }
        try {
            socket = new DatagramSocket();
        } catch (Exception e) {
            System.out.println("Error opening socket");
            System.exit(-1);


        }    
        try{    
            System.out.println("Enter a serie of numbers finished by 0 (write a number and press enter for each one, if a 0 is typed the serie is finished");
            int number = imput.nextInt();
            if (number==0) {
                
                System.exit(0);
                System.out.println("Exit");

                
            }
            else{   

                LinkedList <Integer> list = new LinkedList<>();
                list.add(number);  

                do {
                    number=imput.nextInt();   
                    if (number==0) {
                        imput.close();
                        break;
                    }        
                    list.add(number);       
                    
                } while(number!=0);
                
                ByteBuffer buffer = ByteBuffer.allocate((list.size()*4));
                java.util.Iterator<Integer> iterator = list.iterator();  
                int n=0;
                while (iterator.hasNext()) {
                    n=iterator.next();
                    buffer.putInt(n);

                }
                buffer.rewind();
                DatagramPacket packet = new DatagramPacket(buffer.array(), buffer.array().length,address,port);
                
                
                socket.send(packet);



                byte [] recdata = new byte[1024];
                ByteBuffer recbuffer = ByteBuffer.wrap(recdata);
                DatagramPacket receivep = new DatagramPacket(recbuffer.array(), recbuffer.array().length);
                
                socket.setSoTimeout(10000);
                socket.receive(receivep);                
                System.out.println(recbuffer.getInt());


                socket.close();
            }

        }
        catch(SocketTimeoutException e){
            System.out.println("Timeout (10s)");
        }
        catch (Exception e) {                         
            System.out.print("Error");
            System.exit(-1);

        }
        
        
        
        

    }

    
}