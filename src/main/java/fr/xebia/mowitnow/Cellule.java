package fr.xebia.mowitnow;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString(of = { "position", "occupe", "tondu" })
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

  public void verouiller() {
    occupe = true;
  }

  public void deverouiller() {
    occupe = false;
  }

  public void addVoisin(final Orientation orientation, final Cellule cellule) {
    this.voisins.put(orientation, cellule);
  }

  public Cellule getVoisin(final Orientation orientation) {
    return voisins.get(orientation);
  }
}
