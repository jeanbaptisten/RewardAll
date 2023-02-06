package me.jb.rewardall.database;

import java.sql.Connection;
import java.sql.SQLException;

public interface DbConnection {
  void open() throws SQLException, ClassNotFoundException;

  void close() throws SQLException;

  boolean isOpened() throws SQLException;

  Connection getConnection();
}
