/**
 * A SávÁllapot egy enumeráció, amelynek a felelőssége, hogy előre rögzített konstansokat biztosítson a Sáv állapotainak leírására.
 */

public enum SavAllapot {
    /**
     * A sávon nincs hó, se jég.
     */
    TISZTA,

    /**
     * A sávot hóréteg borítja.
     */
    HAVAS,

    /**
     * A sávon jégréteg található.
     */
    JEGPANCEL,

    /**
     * A jégpáncélt már felbontották, tehát a sávon feltört jég található.
     */
    FELTORT_JEG,

    /**
     * A sávon só található.
     */
    SOZOTT,

    /**
     * A sáv blokkolt, tehát vagy egy autó mozgásképtelen a sávon, vagy egy hó domb található a sávon.
     */
    BLOKKOLT
}
