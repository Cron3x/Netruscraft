package de.cron3x.netruscraft.client.huds;

public class SelectedSpellData {
    private static int index = 0;

    public static int get(){
        return SelectedSpellData.index;
    }

    public static void set(int index) {
        SelectedSpellData.index = index;
    }
}
