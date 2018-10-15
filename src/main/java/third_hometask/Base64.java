package third_hometask;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Base64 {
    final String Alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";

    public static void main(String[] args) throws IOException {
        String s = "Knowledge is power... ну а как по другому?";
        System.out.println(s);
        Base64 base64 = new Base64();
        String sOut = base64.coder(s);
        System.out.println(sOut);
        System.out.println(base64.decoder(sOut));

        base64.cStream("C:\\Users\\Константин\\IdeaProjects\\projects\\notes3.txt");
        base64.cStream("");
    }

    static byte[] getStr3(byte[] a) {
        byte res[] = new byte[3];
        int b_1, b_2;
        b_1 = a[0];
        b_1 <<= 2;
        b_2 = a[1] >> 4;
        res[0] = (byte) (b_1 + b_2);

        b_1 = a[1] & 0x0F;
        b_1 <<= 4;
        b_2 = a[2];
        b_2 >>= 2;
        b_2 = b_2 & 0x0F;
        res[1] = (byte) (b_1 + b_2);

        b_1 = a[2] & 0x03;
        b_1 <<= 6;
        b_2 = a[3];

        res[2] = (byte) (b_1 + b_2);
        return res;
    }

    public String coder(String s) {
        while (s.getBytes().length % 3 != 0) s = s + "=";   //дополнить пробелами до длины кратной 3
        byte[] bbIn = s.getBytes();                         //входной массив
        byte[] bb4 = new byte[4];

        int[] a = new int[3];

        int bbIn_i = 0;   //индекс по входному массиву
        String sOut = "";

        while (bbIn_i < bbIn.length) {
            for (int j = 0; j < 3; j++)           //набрать 3 байта для кодирования
                a[j] = bbIn[bbIn_i + j] >= 0 ? bbIn[bbIn_i + j] : bbIn[bbIn_i + j] + 256; //кириллица
            bb4 = getStr4(a);

            for (byte b : bb4)
                sOut = sOut + this.Alphabet.charAt(b);

            bbIn_i += 3;
        }
        return sOut;
    }

    byte[] getStr4(int[] a) {
        byte[] res = new byte[4];
        int b_1, b_2;

        // первый выходной байт - 6 старших бит
        res[0] = (byte) (a[0] >> 2);

        // второй выходной байт - 2+4
        b_1 = a[0] & 0x03;
        b_1 <<= 4;
        b_2 = a[1];
        b_2 >>= 4;
        res[1] = (byte) (b_1 + b_2);

        // третий выходной байт - 4+2
        b_1 = a[1] & 0x0F;
        b_1 <<= 2;
        b_2 = a[2];
        b_2 >>= 6;
        res[2] = (byte) (b_1 + b_2);

        //четвертый байт 6 младших бит
        res[3] = (byte) (a[2] & 0x3F);
        return res;
    }

    String decoder(String sIn) {
        String sOut = "";
        byte[] bbOut = new byte[sIn.length() / 4 * 3];
        byte[] a = new byte[4];
        byte[] bb3 = new byte[3];
        int bbOut_i = 0;

        int i = 0;
        while (i < sIn.length()) {
            for (int j = 0; j < 4; j++)
                a[j] = i + j < sIn.length() ? (byte) Alphabet.indexOf(sIn.charAt(i + j)) : 0;

            bb3 = getStr3(a);
            for (byte b : bb3)
                bbOut[bbOut_i++] = b;

            i += 4;
        }
        sOut = new String(bbOut);
        return sOut;
    }
    public void cStream(String fName) throws IOException {
        Scanner sc;
        FileWriter fw = null;
        if (fName == "") sc = new Scanner(System.in);
        else {
            sc = new Scanner(new FileInputStream(fName));
            fw = new FileWriter(fName + ".base64");
        }
        int[] a = new int[3];
        byte[] bb4 = new byte[4];
        String bs;
        String sOut = "";
        if (fName == "") System.out.print("Enter text>>");
        while (sc.hasNextLine()) {
            bs = sc.nextLine();
            sOut = coder(bs);
            System.out.println(sOut);
            if (fw == null) break;
            else fw.write(sOut);
        }
        if (fw != null) fw.close();
        sc.close();
    }
}
