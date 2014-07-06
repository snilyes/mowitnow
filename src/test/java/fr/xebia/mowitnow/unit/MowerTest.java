package fr.xebia.mowitnow.unit;

import static fr.xebia.mowitnow.unit.Constant.A;
import static fr.xebia.mowitnow.unit.Constant.D;
import static fr.xebia.mowitnow.unit.Constant.EAST;
import static fr.xebia.mowitnow.unit.Constant.G;
import static fr.xebia.mowitnow.unit.Constant.NORTH;
import static fr.xebia.mowitnow.unit.Constant.SOUTH;
import static fr.xebia.mowitnow.unit.Constant.WEST;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Observable;
import java.util.Observer;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.common.collect.Lists;

import fr.xebia.mowitnow.base.Cell;
import fr.xebia.mowitnow.base.Orientation;
import fr.xebia.mowitnow.mower.Instruction;
import fr.xebia.mowitnow.mower.Lawn;
import fr.xebia.mowitnow.mower.Mower;
import fr.xebia.mowitnow.unit.MowerTest.Data.DataBuilder;
import fr.xebia.mowitnow.util.AsciiGrid;

@Slf4j
@RunWith(JUnitParamsRunner.class)
public class MowerTest {

  @Test
  @Parameters
  public void startTest(final Data data) {
    data.mowers.start();
    assertEquals(data.expectedOrientation, data.mowers.getOrientation());
    assertEquals(data.expectedCell, data.mowers.getCell());
  }

  public Object[][] parametersForStartTest() {
    return new Object[][] {
        {DataBuilder.lawn(2, 2).mower(0, 0, EAST).expected(0, 0, EAST)},
        {DataBuilder.lawn(1, 1).mower(0, 0, EAST).todo(D).expected(0, 0, SOUTH)},
        {DataBuilder.lawn(1, 1).mower(0, 0, EAST).todo(G).expected(0, 0, NORTH)},
        {DataBuilder.lawn(2, 2).mower(0, 0, WEST).todo(G, G, G, G).expected(0, 0, WEST)},
        {DataBuilder.lawn(5, 5).mower(2, 2, WEST).todo(D, A, A, G, A, A, G, A, G, A)
            .expected(1, 3, EAST)},};
  }

  @lombok.Data
  static class Data implements Observer {

    private Lawn lawn;
    private Mower mowers;
    private Cell expectedCell;
    private Orientation expectedOrientation;

    Cell on(final int x, final int y) {
      return lawn.cellAt(x, y);
    }

    @Override
    public void update(Observable o, Object arg) {
      log.debug(AsciiGrid.draw(lawn, mowers));
    }

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    static class DataBuilder {

      final Data data = new Data();

      static DataBuilder lawn(final int largeur, final int longueur) {
        DataBuilder builder = new DataBuilder();
        builder.data.lawn = new Lawn(largeur, longueur);
        return builder;
      }

      DataBuilder mower(final int x, final int y, final Orientation o) {
        data.mowers = new Mower(data.on(x, y), o);
        data.mowers.addObserver(data);
        return this;
      }

      DataBuilder todo(final Instruction... instructions) {
        data.mowers.setInstructions(Lists.newLinkedList(Arrays.asList(instructions)));
        return this;
      }

      Data expected(final int x, final int y, final Orientation o) {
        data.expectedCell = data.on(x, y);
        data.expectedOrientation = o;
        return data;
      }
    }
  }
}
