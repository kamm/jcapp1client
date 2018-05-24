package pl.kmetrak.jcapp1client;

import javax.smartcardio.Card;
import javax.smartcardio.CardChannel;
import javax.smartcardio.CardTerminal;
import javax.smartcardio.CommandAPDU;
import javax.smartcardio.ResponseAPDU;
import javax.smartcardio.TerminalFactory;

@SuppressWarnings("restriction")
public class App 
{
    public static void main( String[] args )
    {
        try {
            byte[] appletId = {(byte) 0xbb, (byte) 0xbb, (byte) 0xcc, (byte) 0xdd, (byte) 0xee, (byte) 0x01, (byte) 0x01};
            TerminalFactory factory = TerminalFactory.getDefault();
            CardTerminal terminal = factory.terminals().getTerminal("Alcor Micro USB Smart Card Reader 0");
            Card card = terminal.connect("*");
            CardChannel channel = card.getBasicChannel();

            CommandAPDU apdu = null;
            ResponseAPDU rapdu = null;
            apdu = new CommandAPDU(0x00, 0xA4, 0x04, 0x00, appletId);
            rapdu = channel.transmit(apdu);
            apdu = new CommandAPDU(0xB0, 0x01, 0x00, 0x00, 13);
            rapdu = channel.transmit(apdu);

            System.out.println(new String(rapdu.getData()));

            card.disconnect(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
