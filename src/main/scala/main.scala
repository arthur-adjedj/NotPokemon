import swing._

object MyApp extends SimpleSwingApplication {
    def top = new MainFrame {
        title = "My App"
        contents = new BorderPanel {
            add(new Label((new Pikachu).toString), BorderPanel.Position.North)
            add(new Label(ShellSmash.toString), BorderPanel.Position.Center)
            add(new Label((new Carapuce).toString), BorderPanel.Position.South)
        }
    }
}