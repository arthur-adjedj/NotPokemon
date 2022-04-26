trait Saveable {
    def toStringSave (tabs : Int) : String = ""
    def toStringSave : String = toStringSave(0)
}