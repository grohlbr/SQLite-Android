package com.rocha.arthur.gerenciarveiculo.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.rocha.arthur.gerenciarveiculo.database.DBHelper;
import com.rocha.arthur.gerenciarveiculo.model.Veiculo;

import java.util.ArrayList;

/**
 * Created by arthur on 06/07/16.
 */

public class VeiculoDAO {
    private Context context;
    private DBHelper dbHelper;

    public static final String sqlCreateTable =
            "CREATE TABLE VEICULOS (" +
            "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
            "MARCA TEXT," +
            "MODELO TEXT,"+
            "PLACA TEXT," +
            "ANO TEXT)";

    public VeiculoDAO(Context context){
        this.context = context;
        this.dbHelper = new DBHelper(this.context);
    }

    public Long save(Veiculo veiculo){
        ContentValues values = new ContentValues();
        values.put("MARCA", veiculo.getMarca());
        values.put("MODELO", veiculo.getModelo());
        values.put("PLACA", veiculo.getPlaca());
        values.put("ANO", veiculo.getAno());

        SQLiteDatabase database = dbHelper.getWritableDatabase();
        try {
            Long id = database.insertOrThrow("VEICULOS",null,values);
            Log.i("DATABASE", "INSERT: " + veiculo.getPlaca());
            return id;
        }catch (SQLiteConstraintException ex){
            database.replace("VEICULOS",null,values);
            Log.i("DATABASE", "UPDATE: " + veiculo.getPlaca());
            return veiculo.getId();
        }
        catch (Throwable e){
            throw e;
        }
        finally {
            database.close();
        }
    }

    public int update(Veiculo veiculo){
        ContentValues values = new ContentValues();
        values.put("MARCA", veiculo.getMarca());
        values.put("MODELO", veiculo.getModelo());
        values.put("PLACA", veiculo.getPlaca());
        values.put("ANO", veiculo.getAno());

        SQLiteDatabase database = dbHelper.getWritableDatabase();
        try {
            int id = database.update("VEICULOS",values,"ID=?", new String[]{veiculo.getId().toString()});
            return id;
        }catch (Throwable e){
            throw e;
        }
        finally {
            database.close();
        }
    }

    public Integer delete(Veiculo veiculo){
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        try {
            Integer id = database.delete("VEICULOS", "ID=?", new String[]{veiculo.getId().toString()});
            return id;
        }catch (Throwable e){
            throw e;
        }
        finally {
            database.close();
        }
    }

    public ArrayList<Veiculo> getAll(){
        SQLiteDatabase database = dbHelper.getReadableDatabase();

        String[] colums = new String[]{"ID", "MARCA", "MODELO", "PLACA", "ANO"};
        String where = null;
        String[] whereArgs = null;
        String groupBy = null;
        String having = null;
        String orderBy = null; // "MARCA ASC"...
        String limit = null;

        Cursor cursor = database.query("VEICULOS",colums,where,whereArgs,groupBy,having,orderBy,limit);

        ArrayList<Veiculo> veiculos = new ArrayList<>();

        if(cursor.moveToFirst()){
            do {
                veiculos.add(getVeiculoByCursor(cursor));
            }while (cursor.moveToNext());
        }

        return veiculos;

    }

    private Veiculo getVeiculoByCursor(Cursor cursor){
        Veiculo veiculo = new Veiculo();
        veiculo.setId(cursor.getLong(cursor.getColumnIndex("ID")));
        veiculo.setMarca(cursor.getString(cursor.getColumnIndex("MARCA")));
        veiculo.setModelo(cursor.getString(cursor.getColumnIndex("MODELO")));
        veiculo.setPlaca(cursor.getString(cursor.getColumnIndex("PLACA")));
        veiculo.setAno(cursor.getString(cursor.getColumnIndex("ANO")));

        return veiculo;
    }


}
