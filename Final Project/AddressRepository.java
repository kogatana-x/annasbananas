
public class AddressRepository {
    private String filename="address.txt";
    Database AddressDatabase = new Database(filename);

    public void saveAddress(Address address){
        String row=address.getId()+","+address.getStreet1()+","+address.getStreet2()+","+address.getCity()+","+address.getState()+","+address.getZip()+","+address.getCountry();
        AddressDatabase.add(row);
    }

    


    
}
