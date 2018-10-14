package third_hometask;

public class Base64 {
    public static void main(String args[]) {
        String s = "Encoder by base64 for java. C учётом кириллицы между прочим:)";
        System.out.println(s);
        System.out.println(decoder(coder(s)));
    }

    public static byte[] coder(String s) {
        while (s.getBytes().length % 3 != 0) s = s + " ";   //дополнить пробелами до длины кратной 3
        byte[] bbIn = s.getBytes();                         //входной массив

        byte[] bbOut = new byte[bbIn.length / 3 * 4];       //выходной массив
        byte[] bb4 = new byte[4];

        int[] a = new int[3];

        System.out.println("Входной массив для кодирования (" + bbIn.length + " байт):");
        for (byte b : bbIn)
            System.out.print((b >= 0 ? b : b + 256) + " ");
        System.out.println();

        int bbIn_i = 0;   //индекс по входному массиву
        int bbOut_i = 0;  //индекс по выходному массиву

        while (bbIn_i < bbIn.length) {
            for (int j = 0; j < 3; j++) {          //набрать 3 байта для кодирования
                a[j] = bbIn_i + j < bbIn.length ?  //отрицательные байты(кириллица) делать положительными
                        (bbIn[bbIn_i + j] >= 0 ?
                                bbIn[bbIn_i + j] : bbIn[bbIn_i + j] + 256) : 0;
            }
            bb4 = getStr4(a);

            for (byte b : bb4)
                bbOut[bbOut_i++] = b;
            bbIn_i += 3;
        }
        return bbOut;
    }

    static byte[] getStr4(int[] a) {
        byte[] res = new byte[4];
        int b1, b2, b2_1, b2_2, b3, b3_1, b3_2, b4;
        // первый выходной байт - 6 старших бит
        b1 = a[0];
        b1 >>= 2;
        // второй выходной бай - 2+4
        b2_1 = a[0] & 0x03;
        b2_1 <<= 4;
        b2_2 = a[1];
        b2_2 >>= 4;
        b2 = b2_1 + b2_2;
        // третий выходной байт - 4+2
        b3_1 = a[1] & 0x0F;
        b3_1 <<= 2;
        b3_2 = a[2];
        b3_2 >>= 6;
        b3 = b3_1 + b3_2;
        //четвертый байт 6 младших бит
        b4 = a[2] & 0x3F;

        res[0] = (byte) b1;
        res[1] = (byte) b2;
        res[2] = (byte) b3;
        res[3] = (byte) b4;
        return res;
    }

    static String decoder(byte[] bbIn) {
        String sOut = "";
        byte[] bbOut = new byte[bbIn.length / 4 * 3];
        byte[] a = new byte[4];
        byte[] bb3 = new byte[3];
        int bbOut_i = 0;

        System.out.println("Входной массив base64 (" + bbIn.length + "):");
        for (byte b : bbIn)
            System.out.print(b + " ");
        System.out.println();

        int i = 0;
        while (i < bbIn.length) {
            for (int j = 0; j < 4; j++) {
                a[j] = i + j < bbIn.length ? bbIn[i + j] : 0;
            }
            bb3 = getStr3(a);
            for (byte b : bb3)
                bbOut[bbOut_i++] = b;
            i += 4;
        }
        sOut = new String(bbOut);
        return sOut;
    }

    static byte[] getStr3(byte[] a) {
        byte res[] = new byte[3];
        int b1, b1_1, b1_2, b2, b2_1, b2_2, b3, b3_1, b3_2;
        b1_1 = a[0];
        b1_1 <<= 2;
        b1_2 = a[1] >> 4;
        b1 = b1_1 + b1_2;

        b2_1 = a[1] & 0x0F;
        b2_1 <<= 4;
        b2_2 = a[2];
        b2_2 >>= 2;
        b2_2 = b2_2 & 0x0F;
        b2 = b2_1 + b2_2;

        b3_1 = a[2] & 0x03;
        b3_1 <<= 6;
        b3_2 = a[3];
        b3 = b3_1 + b3_2;

        res[0] = (byte) b1;
        res[1] = (byte) b2;
        res[2] = (byte) b3;
        return res;
    }
}
