package logistics.data.util;

import java.util.List;

public class ByteUtil {

	public static String byte2HexStr(byte[] b) {  
	    String hs = "";  
	    String stmp = "";  
	    for (int n = 0; n < b.length; n++) {  
	        stmp = (Integer.toHexString(b[n] & 0XFF));  
	        if (stmp.length() == 1)  
	            hs = hs + "0" + stmp;  
	        else  
	            hs = hs + stmp;  
	        // if (n<b.length-1) hs=hs+":";  
	    }  
	    return hs.toUpperCase();  
	}  
	
	public static String byte2LowHexStr(byte[] b) {  
	    String hs = "";  
	    String stmp = "";  
	    for (int n = 0; n < b.length; n++) {  
	        stmp = (Integer.toHexString(b[n] & 0XFF));  
	        if (stmp.length() == 1)  
	            hs = hs + stmp;  
	        else  
	            hs = hs + stmp.charAt(1);  
	        // if (n<b.length-1) hs=hs+":";  
	    }  
	    return hs.toUpperCase();  
	}  
	
	public static byte[] hexStr2Bytes(String src) {  
	    int m = 0, n = 0;  
	    int l = src.length() / 2;  
	    //System.out.println(l);  
	    byte[] ret = new byte[l];  
	    for (int i = 0; i < l; i++) {  
	        m = i * 2 + 1;  
	        n = m + 1;  
	        ret[i] = uniteBytes(src.substring(i * 2, m), src.substring(m, n));  
	    }  
	    return ret;  
	}  
	
	private static byte uniteBytes(String src0, String src1) {  
	    byte b0 = Byte.decode("0x" + src0).byteValue();  
	    b0 = (byte) (b0 << 4);  
	    byte b1 = Byte.decode("0x" + src1).byteValue();  
	    byte ret = (byte) (b0 | b1);  
	    return ret;  
	}
	
	public static int OxStringtoInt(String ox) throws Exception {  
        ox=ox.toLowerCase();  
        if(ox.startsWith("0x")){  
            ox=ox.substring(2, ox.length() );  
        }  
        int ri = 0;  
        int oxlen = ox.length();  
        if (oxlen > 8)  
            throw (new Exception("too lang"));  
        for (int i = 0; i < oxlen; i++) {  
            char c = ox.charAt(i);  
            int h;  
            if (('0' <= c && c <= '9')) {  
                h = c - 48;  
            } else if (('a' <= c && c <= 'f'))  
            {  
                h = c - 87;  
  
            }  
            else if ('A' <= c && c <= 'F') {  
                h = c - 55;  
            } else {  
                throw (new Exception("not a integer "));  
            }  
            byte left = (byte) ((oxlen - i - 1) * 4);  
            ri |= (h << left);  
        }  
        return ri;  
  
    } 
	
	public static void printHexString(byte[] b) {
		for (int i = 0; i < b.length; i++) {
			String hex = Integer.toHexString(b[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			System.out.print(hex.toUpperCase());
		}

	}
	
	public static byte[] subByteList(List<Byte> byteList, int begin, int end) {
		
		if(byteList == null || begin < 0 || end >= byteList.size() || begin > end)
			return null;
		
		byte[] byteArray = new byte[end - begin + 1];
		
		for(int i = 0; i < byteArray.length; i++) {
			byteArray[i] = byteList.get(begin + i);
		}
		
		return byteArray;
	}
	
	public static String int2HexStr(int n, int hexLength) {
		String hexStr = Integer.toHexString(n);
		String out = "";
		
		if(hexStr.length() < hexLength) {
			for(int i = hexLength - hexStr.length(); i > 0; i--) {
				out = out + "0";
			}
			out = out + hexStr;
		} else if(hexStr.length() > hexLength) {
			hexStr = hexStr.substring(hexStr.length() - hexLength, hexStr.length());
		} else {
			out = hexStr;
		}
		
		return out;
	}
}
