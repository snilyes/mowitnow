package fr.xebia.mowitnow.base;

import java.util.HashMap;
import java.util.Map;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString(exclude = {"voisins"})
@EqualsAndHashCode(exclude = {"voisins"})
@RequiredArgsConstructor
public class Cellule {

  @NonNull
  @Getter
  private final Position position;

  @Getter
  private boolean occupe;

  @Getter
  @Setter
  private boolean tondu;

  private final Map<Orientation, Cellule> voisins = new HashMap<Orientation, Cellule>(
      Orientation.values().length);

  public void occuper() {
    occupe = true;
  }

  public void liberer() {
    occupe = false;
  }

  public void addVoisin(final Orientation orientation, final Cellule cellule) {
    this.voisins.put(orientation, cellule);
  }

  public Cellule getVoisin(final Orientation orientation) {
    return voisins.get(orientation);
  }
}
