package test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.swing.JOptionPane;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseCredentials;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
public class TestClass {
	public TestClass(){
		//alert("called");
		final AtomicBoolean done = new AtomicBoolean(false);
		final AtomicBoolean doneRead = new AtomicBoolean(false);
		FileInputStream serviceAccount = null;
		try {
			serviceAccount = new FileInputStream("pente-7d19d-firebase-adminsdk-1g71j-4745f3f7d7.json");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(serviceAccount != null){
			FirebaseOptions options = new FirebaseOptions.Builder()
					.setCredential(FirebaseCredentials.fromCertificate(serviceAccount))
					.setDatabaseUrl("https://pente-7d19d.firebaseio.com/")
					.build();
		
			FirebaseApp.initializeApp(options);
			DatabaseReference ref = FirebaseDatabase
				    .getInstance()
				    .getReference();
			ref.setValue("Koreans are the scum of the earth", 
					new DatabaseReference.CompletionListener() {
						
						@Override
						public void onComplete(DatabaseError arg0, DatabaseReference arg1) {
							done.set(true);
							// TODO Auto-generated method stub
							
						}
					});while(!done.get());
			ref.addValueEventListener(new ValueEventListener() {
				@Override
				 public void onDataChange(DataSnapshot dataSnapshot) {
					alert(dataSnapshot.getValue(String.class));
					doneRead.set(true);
					
			    }
	
			    @Override
			    public void onCancelled(DatabaseError databaseError) {
			        // ...
			    }
			});while(!doneRead.get());
		}
		
		
		
	}
	public void alert(String m){
		JOptionPane.showMessageDialog(null, m);
	}
	
}
