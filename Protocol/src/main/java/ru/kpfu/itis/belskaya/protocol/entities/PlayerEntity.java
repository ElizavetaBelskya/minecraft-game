package ru.kpfu.itis.belskaya.protocol.entities;

import java.io.Serializable;
import java.util.Objects;

public class PlayerEntity implements Comparable, Serializable {
    int id;
    int xCoordinate;
    int yCoordinate;


    public PlayerEntity(int id, int xCoordinate, int yCoordinate) {
        this.id = id;
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getxCoordinate() {
        return xCoordinate;
    }

    public void setxCoordinate(int xCoordinate) {
        this.xCoordinate = xCoordinate;
    }

    public int getyCoordinate() {
        return yCoordinate;
    }

    public void setyCoordinate(int yCoordinate) {
        this.yCoordinate = yCoordinate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PlayerEntity)) return false;
        PlayerEntity that = (PlayerEntity) o;
        return id == that.id && xCoordinate == that.xCoordinate && yCoordinate == that.yCoordinate;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, xCoordinate, yCoordinate);
    }


    @Override
    public String toString() {
        return "PlayerEntity{" +
                "id=" + id +
                ", xCoordinate=" + xCoordinate +
                ", yCoordinate=" + yCoordinate +
                '}';
    }


    @Override
    public int compareTo(Object o) {
        //TODO: нормальный сделай братан
        if (o instanceof PlayerEntity) {
            PlayerEntity playerEntity = (PlayerEntity) o;
            if (this.getId() == playerEntity.getId()) {
                return 0;
            } else if (this.getId() > playerEntity.getId()) {
                return 1;
            } else if (this.getId() < playerEntity.getId()) {
                return -1;
            }
        }
        return 2;
    }
}
