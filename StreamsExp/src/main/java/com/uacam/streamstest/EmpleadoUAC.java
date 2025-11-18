/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uacam.streamstest;

import java.io.Serializable;

/**
 *
 * @author sergi
 */
public class EmpleadoUAC implements Serializable {
  private static final long serialVersionUID = 1L;

  String name;
  String email;
  String workStation;

  public EmpleadoUAC(String name, String email, String workStation) {
    this.name = name;
    this.email = email;
    this.workStation = workStation;
  }

  @Override
  public String toString() {
    String empleadojson;
    empleadojson = "{" +
        "nombre:" + this.name +
        ", correo:" + this.email +
        ", lugarTrabajo:" + this.workStation +
        "}";
    return empleadojson;
  }

}
