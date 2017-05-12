package test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import javax.swing.JOptionPane;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseCredentials;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FireBasePente {
	public String color = "Black";
	DatabaseReference roomRef;
	DatabaseReference turnRef;
	int x = 2;
	public FireBasePente(){
		
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
			roomRef = ref.child("Rooms").child(FireRead(ref.child("CurrentRoom")));
			String read = (FireRead(roomRef.child("Players")));
			alert(read);
			if(read.matches("1")){
				alert("hit");
				color = "White";
				FireWrite(roomRef.child("Players"), "2");
				int cr = Integer.parseInt(FireRead(ref.child("CurrentRoom")));
				FireWrite(ref.child("CurrentRoom"), Integer.toString(cr+1));
				FireWrite(ref.child("Rooms").child(Integer.toString(cr+1)).child("Players"), "0");
				FireWrite(ref.child("Rooms").child(Integer.toString(cr+1)).child("Turns").child("White"), "init");
				FireWrite(ref.child("Rooms").child(Integer.toString(cr+1)).child("Turns").child("Black"), "init");
			}else{
				color = "Black";
				FireWrite(roomRef.child("Players"), "1");
			}
			alert(FireRead(roomRef.child("Players")));
		}
	}
	public void MakeNextMove(int row, int column){
		String toWrite = row + ", " + column;
		String ocolor = "White";
		if(color == "White"){
			ocolor = "Black";
		}
		//String popmove = FireRead(roomRef.child("Turns").child(ocolor));
		FireWrite(roomRef.child("Turns").child(color), toWrite);
	}
	public String ListenNextMove(){
		String ocolor = "White";
		if(color == "White"){
			ocolor = "Black";
		}
		return FireRead(roomRef.child("Turns").child(ocolor));
	}
	public String FireRead(DatabaseReference dbr){
		final AtomicBoolean doneRead = new AtomicBoolean(false);
		final AtomicReference<String> toReturn = new AtomicReference<String>("null");
		dbr.addValueEventListener(new ValueEventListener() {
			@Override
			 public void onDataChange(DataSnapshot dataSnapshot) {
				toReturn.set(dataSnapshot.getValue(String.class));
				doneRead.set(true);
				
		    }

		    @Override
		    public void onCancelled(DatabaseError databaseError) {
		        // ...
		    }
		});while(!doneRead.get());
		return toReturn.get();
	}
	public void FireWrite(DatabaseReference dbr, String toWrite){
		final AtomicBoolean done = new AtomicBoolean(false);
		dbr.setValue(toWrite, 
				new DatabaseReference.CompletionListener() {
					
					@Override
					public void onComplete(DatabaseError arg0, DatabaseReference arg1) {
						done.set(true);
						// TODO Auto-generated method stub
						
					}
				});while(!done.get());
	}
	public void alert(String m){
		JOptionPane.showMessageDialog(null, m);
	}
	public Square readString(String toRead, Square[][] theBoard){
		int commaIndex = toRead.indexOf(',');
		int row = Integer.parseInt(toRead.substring(0, commaIndex));
		int col = Integer.parseInt(toRead.substring(commaIndex+2));
		return theBoard[row][col];
	}
}
