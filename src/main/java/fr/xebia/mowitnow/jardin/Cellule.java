package fr.xebia.mowitnow.jardin;

import java.util.HashMap;
import java.util.Map;

import fr.xebia.mowitnow.base.Orientation;
import fr.xebia.mowitnow.base.Position;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString(of = {"position", "occupe", "tondu"})
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
