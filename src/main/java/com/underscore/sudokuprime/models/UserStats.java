package com.underscore.sudokuprime.models;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.io.Serializable;
import java.lang.String;

@Data
public class UserStats implements Serializable {

  private String cellsFilled;

  private String mistakes;

  private String corrects;

  public String getCells_filled() {
    return this.cellsFilled;
  }

  public void setCells_filled(String cells_filled) {
    this.cellsFilled = cells_filled;
  }

  public String getMistakes() {
    return this.mistakes;
  }

  public void setMistakes(String mistakes) {
    this.mistakes = mistakes;
  }

  public String getCorrects() {
    return this.corrects;
  }

  public void setCorrects(String corrects) {
    this.corrects = corrects;
  }
}
