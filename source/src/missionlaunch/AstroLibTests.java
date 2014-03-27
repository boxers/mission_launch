package missionlaunch;

import com.mhuss.AstroLib.AstroDate;
import com.mhuss.AstroLib.NoInitException;
import com.mhuss.AstroLib.ObsInfo;
import com.mhuss.AstroLib.PlanetData;
import com.mhuss.AstroLib.Planets;

public class AstroLibTests {
    public static void main(String[] args) {
        AstroDate date = new AstroDate(19,4,1990,0,0,0);
        System.out.println(date.jd());
        PlanetData mars = new PlanetData(Planets.MERCURY, date.jd(),new ObsInfo());
        try{
            System.out.println(mars.getPolarRadius());
            System.out.println(mars.getPolarLon());
            System.out.println(mars.getPolarLat());
        }
        catch(NoInitException noi){System.out.println("Error getting heliocentric coordinates.");}
    }
}
