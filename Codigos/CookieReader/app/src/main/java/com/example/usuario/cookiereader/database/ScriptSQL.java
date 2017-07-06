package com.example.usuario.cookiereader.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Paulo on 28/03/2015.
 */
public class ScriptSQL {

       public static String getUf()
       {
            String sql = "CREATE TABLE IF NOT EXISTS Uf ( " +
                    "  siglaUf        TEXT   PRIMARY KEY  NOT NULL, " +
                    "  nome           TEXT  NOT NULL);";

            return sql;
       }


     public static String getCidade()
     {
          String sql = ("CREATE TABLE IF NOT EXISTS Cidade " +
                  "( _cdCidade INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                  " nome TEXT NOT NULL, " +
                  " siglaUf TEXT NOT NULL);");

          return sql;
     }

     public static String getUsuario()
     {
          String sql = "CREATE TABLE IF NOT EXISTS Usuario (" +
                  "  _cdUsuario        INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                  "  nome             TEXT  NOT NULL, " +
                  "  login            TEXT  NOT NULL, " +
                  "  senha            TEXT  NOT NULL, " +
                  "  cpf              TEXT  NOT NULL, " +
                  "  quantEscaneamento              INTEGER, " +
                  "  dataSessao       DATETIME, " +
                  "  cdCidade         INTEGER NOT NULL);";

          return sql;
     }

     public static String getEmpresa()
     {
          String sql = "CREATE TABLE IF NOT EXISTS Empresa ( " +
                  "  _cdEmpresa        INTEGER  PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                  "  nome             TEXT                  NOT NULL, " +
                  "  cdCidade         INTEGER               NOT NULL);";

          return sql;
     }

     public static String getBiscoito()
     {
          String sql = ("CREATE TABLE IF NOT EXISTS Biscoito " +
                  "( _cdBiscoito INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                  " nome TEXT NOT NULL, " +
                  " cdBarras TEXT NOT NULL, " +
                  " cdEmpresa INTEGER NOT NULL," +
                  " CONSTRAINT cdBarras_unico UNIQUE (cdBarras));");

          return sql;
     }

     public static String getNutriente()
     {
          String sql = "CREATE TABLE IF NOT EXISTS  Nutriente ( " +
                  "  _cdNutriente         INTEGER   PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                  "  nome                TEXT                    NOT NULL);";

          return sql;
     }

     public static String getDcnt()
     {
          String sql = "CREATE TABLE IF NOT EXISTS dcnt ( " +
                  "  _cdDcnt         INTEGER   PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                  "  nome           TEXT                    NOT NULL);";

          return sql;
     }

     public static String getBiscoitoNutriente()
     {
          String sql = "CREATE TABLE IF NOT EXISTS  BiscoitoNutriente ( " +
                  "  cdBiscoito         INTEGER      NOT NULL, " +
                  "  cdNutriente        INTEGER      NOT NULL, " +
                  "  quant              REAL         NOT NULL, " +
                  "  PRIMARY KEY (cdBiscoito, cdNutriente)," +
                  "  FOREIGN KEY (cdBiscoito) references Biscoito(_cdBiscoito) ON DELETE CASCADE," +
                  "  FOREIGN KEY (cdNutriente) references Nutriente(_cdNutriente) ON DELETE CASCADE);";

          return sql;
     }

     public static String getDCNTpeso()
     {
          String sql = "CREATE TABLE IF NOT EXISTS  DCNTpeso ( " +
                  "  cdDcnt         INTEGER      NOT NULL, " +
                  "  cdNutriente    INTEGER      NOT NULL, " +
                  "  peso           INTEGER      NOT NULL, " +
                  "  PRIMARY KEY (cdDcnt, cdNutriente)," +
                  "  FOREIGN KEY (cdDcnt) references dcnt(_cdDcnt) ON DELETE CASCADE," +
                  "  FOREIGN KEY (cdNutriente) references Nutriente(_cdNutriente) ON DELETE CASCADE);";

          return sql;
     }

     public static String getScanBiscoito()
     {
          String sql = "CREATE TABLE IF NOT EXISTS ScanBiscoito ( " +
                  "  _cdScan         INTEGER   PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                  "  dataHora       DATETIME         NOT NULL, " +
                  "  cdDcnt         INTEGER      NOT NULL, " +
                  "  cdBiscoito     INTEGER      NOT NULL, " +
                  "  favorito       INTEGER      NOT NULL, " +
                  "  cdUsuario      INTEGER      NOT NULL);";

          return sql;
     }

}
