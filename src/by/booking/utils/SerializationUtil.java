package by.booking.utils;

import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;
import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;

import javax.sql.rowset.serial.SerialBlob;
import java.io.*;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.*;

public class SerializationUtil {

    public static SerialBlob serialize(List<String> obj) throws SQLException{
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(obj);
            oos.flush();
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        SerialBlob blob = new SerialBlob(baos.toByteArray());
        return blob;
    }

    public static List<String> deserialize (Blob blob) throws SQLException{
        List<String> obj = null;
        try {
            ObjectInputStream ois = new ObjectInputStream(blob.getBinaryStream());
            obj = (List<String>) ois.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return obj;
    }

//    public static ByteArrayOutputStream serialize(List<String> obj) {
//        ByteOutputStream baos = new ByteArrayOutputStream();
//        try {
//            ObjectOutputStream oos = new ObjectOutputStream(baos);
//            oos.writeObject(obj);
//            oos.flush();
//            oos.close();
//            return baos;
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return baos;
//    }
//
//    public static List<String> deserialize (InputStream binaryStream) {
//        List<String> obj = null;
//        byte[] bytes = null;
//        try {
//            binaryStream.read(bytes);
//            ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
//            ObjectInputStream ois = new ObjectInputStream(bais);
//            obj = (List<String>) ois.readObject();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//        return obj;
//    }
//    public static void main(String[] args) {
//        ArrayList<String> facilities = new ArrayList<>();
//        facilities.add("safe");
//        facilities.add("wi-fi");
//        facilities.add("flat tv");
//        ByteArrayOutputStream byteArrayOutputStream = serialize(facilities);
//        byte[] bytes = byteArrayOutputStream.toByteArray();
//        ByteArrayInputStream sfd = new ByteArrayInputStream(bytes);
//        List<String > ls = deserialize(sfd);
//        System.out.println(ls.get(1));
//
//    }
}
