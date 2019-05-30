package jaeyeop.co.criminalintent;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jaeyeop.co.criminalintent.database.CrimeBaseHelper;
import jaeyeop.co.criminalintent.database.CrimeCursorWrapper;
import jaeyeop.co.criminalintent.database.CrimeDbSchema;
import jaeyeop.co.criminalintent.database.CrimeDbSchema.CrimeTable;

public class CrimeLab {
    private static CrimeLab crimeLab;
    //private List<Crime> crimes;

    private Context context;
    private SQLiteDatabase database;

    public static CrimeLab get(Context context){
        if(crimeLab == null){
            crimeLab = new CrimeLab(context);
        }
        return crimeLab;
    }

    private CrimeLab(Context context){
        //crimes = new ArrayList<>();
        this.context = context.getApplicationContext();
        database = new CrimeBaseHelper(this.context).getWritableDatabase();


        /*
        for (int i = 0; i < 100; i++){
            Crime crime = new Crime();
            crime.setTitle("범죄 #" + i );
            crime.setSolved(i % 2 == 0);
            crimes.add(crime);
        }
        */
    }

    public void addCrime(Crime c){
        //crimes.add(c);
        ContentValues values = getContentValues(c);
        database.insert(CrimeTable.NAME, null, values);
    }

    public List<Crime> getCrimes(){
        //return crimes;
        //return new ArrayList<>();
        List<Crime> crimes = new ArrayList<>();

        CrimeCursorWrapper cursor = queryCrimes(null, null);

        try{
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                crimes.add(cursor.getCrime());
                cursor.moveToNext();
            }
        }finally {
            cursor.close();
        }
        return crimes;
    }

    public Crime getCrime(UUID id){
        /*for(Crime crime : crimes){
            if(crime.getId().equals(id)){
                return crime;
            }
        }*/
        //return null;

        CrimeCursorWrapper cursor = queryCrimes(CrimeTable.Cols.UUID + " =?", new String[]{id.toString()});
        try{
            if(cursor.getCount() == 0){
                return null;
            }
            cursor.moveToFirst();
            return cursor.getCrime();
        }finally {
            cursor.close();
        }
    }

    public void updateCrime(Crime crime){
        String uuidString = crime.getId().toString();
        ContentValues values = getContentValues(crime);

        database.update(CrimeTable.NAME, values, CrimeTable.Cols.UUID + " =?", new String[]{uuidString});
    }

    private static ContentValues getContentValues(Crime crime){
        ContentValues values = new ContentValues();
        values.put(CrimeTable.Cols.UUID, crime.getId().toString());
        values.put(CrimeTable.Cols.TITLE, crime.getTitle());
        values.put(CrimeTable.Cols.DATE, crime.getDate().getTime());
        values.put(CrimeTable.Cols.SOLVED, crime.isSolved() ? 1 : 0);

        return values;
    }

    //private Cursor queryCrimes(String whereClause, String[] whereArgs){
    private CrimeCursorWrapper queryCrimes(String whereClause, String[] whereArgs){
        Cursor cursor = database.query(CrimeTable.NAME, null, whereClause, whereArgs, null, null, null);
        //return cursor;
        return new CrimeCursorWrapper(cursor);
    }
}
