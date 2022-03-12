
import java.net.*;
import java.nio.ByteBuffer;

public class udpser{
    
    
    public static void main(String [] args){
        
        try {
            if (args.length!=1) throw new Exception("Wrong number of parameters, correct use: \njava udpser port_numer");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(-1);
        }
        int port=Integer.parseInt(args[0]); 

        DatagramSocket socket = null;
        int acumulator=0;

        try {
            socket = new DatagramSocket(port);
        } catch (Exception e) {
            System.out.println("Error opening socket");
            System.exit(-1);


        }    
        try{    


                            
                

                while (true) {
                    
                byte [] data = new byte[1024];
                ByteBuffer databuf = ByteBuffer.wrap(data);

                DatagramPacket receivep = new DatagramPacket(databuf.array(), databuf.array().length);
                socket.receive(receivep);

                int number=0;
                for (int i = 0; i < databuf.array().length; i++) {
                    number =databuf.getInt();
                    if (number==0) break;
                    acumulator=acumulator+number;
                            
                }             
               
                System.out.println(acumulator);

                ByteBuffer sendbuffer = ByteBuffer.allocate(4);
                sendbuffer.putInt(acumulator);


                int cliport=receivep.getPort();
                InetAddress cliaddress = receivep.getAddress();
                DatagramPacket packet = new DatagramPacket(sendbuffer.array(), sendbuffer.array().length,cliaddress,cliport);
                socket.send(packet);


                }
                
            }
        catch (Exception e) {
            System.out.print("Error");
            socket.close();
            System.exit(-1);

        }
        
        
        
        

    }

    
}