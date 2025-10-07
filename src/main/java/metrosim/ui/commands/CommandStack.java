package metrosim.ui.commands;

import java.util.ArrayDeque;
import java.util.Deque;

public final class CommandStack {
  private final Deque<Command> undo = new ArrayDeque<>(), redo = new ArrayDeque<>();

  public void apply(Command c) {
    c.execute();
    undo.push(c);
    redo.clear();
  }

  public void undo() {
    if (!undo.isEmpty()) {
      var c = undo.pop();
      c.undo();
      redo.push(c);
    }
  }

  public void redo() {
    if (!redo.isEmpty()) {
      var c = redo.pop();
      c.execute();
      undo.push(c);
    }
  }
}
