/*
 * Copyright (C) 2016 August Enzelberger <augustenz+github@gmail.com> (https://github.com/augustenz)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package sttcollectionmanager;

/**
 *
 * August Enzelberger <augustenz+github@gmail.com> (https://github.com/augustenz)
 */
public class CrewMember {
    private String crewName;
    private String charName;
    private String stars;
    private String starsFused;
    private String level;
    private boolean fullyEquiped;
    private boolean inVault;
    private String quantity;
    private String cmd;
    private String dip;
    private String eng;
    private String med;
    private String sci;
    private String sec;
    private String race;
    private String[] traits;
    private boolean inCollection;
    
    public void CrewMember() {
        this.crewName="";
        this.charName="";
        this.stars="0";
        this.starsFused="0";
        this.level="0";
        this.fullyEquiped=false;
        this.inVault=false;
        this.quantity="0";
        this.cmd="0";
        this.dip="0";
        this.eng="0";
        this.med="0";
        this.sci="0";
        this.sec="0";
        this.race="";
        this.traits = new String[30];
        this.inCollection=false;
    }
    
    public void CrewMember(String crewName,String charName,String stars,String cmd,String dip,String eng,String med,String sci,String sec,String race,String[] traits,boolean inCollection) {
        this.crewName=crewName;
        this.charName=charName;
        this.stars=stars;
        this.cmd=cmd;
        this.dip=dip;
        this.eng=eng;
        this.med=med;
        this.sci=sci;
        this.sec=sec;
        this.race=race;
        this.traits=traits;
        this.inCollection=inCollection;
    }
    
    public void CrewMember(String crewName,String charName,String stars,String starsFused,String level,boolean fullyEquiped,boolean inVault,String quantity,String cmd,String dip,String eng,String med,String sci,String sec,String race,String[] traits,boolean inCollection) {
        this.crewName=crewName;
        this.charName=charName;
        this.stars=stars;
        this.starsFused=starsFused;
        this.level=level;
        this.fullyEquiped=fullyEquiped;
        this.inVault=inVault;
        this.quantity=quantity;
        this.cmd=cmd;
        this.dip=dip;
        this.eng=eng;
        this.med=med;
        this.sci=sci;
        this.sec=sec;
        this.race=race;
        this.traits=traits;
        this.inCollection=inCollection;
    }
    
    /**
     * @return the crewName
     */
    public String getCrewName() {
        return crewName;
    }

    /**
     * @param crewName the crewName to set
     */
    public void setCrewName(String crewName) {
        this.crewName = crewName;
    }

    /**
     * @return the charName
     */
    public String getCharName() {
        return charName;
    }

    /**
     * @param charName the charName to set
     */
    public void setCharName(String charName) {
        this.charName = charName;
    }

    /**
     * @return the stars
     */
    public String getStars() {
        return stars;
    }

    /**
     * @param stars the stars to set
     */
    public void setStars(String stars) {
        this.stars = stars;
    }

    /**
     * @return the cmd
     */
    public String getCmd() {
        return cmd;
    }

    /**
     * @param cmd the cmd to set
     */
    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    /**
     * @return the dip
     */
    public String getDip() {
        return dip;
    }

    /**
     * @param dip the dip to set
     */
    public void setDip(String dip) {
        this.dip = dip;
    }

    /**
     * @return the eng
     */
    public String getEng() {
        return eng;
    }

    /**
     * @param eng the eng to set
     */
    public void setEng(String eng) {
        this.eng = eng;
    }

    /**
     * @return the med
     */
    public String getMed() {
        return med;
    }

    /**
     * @param med the med to set
     */
    public void setMed(String med) {
        this.med = med;
    }

    /**
     * @return the sci
     */
    public String getSci() {
        return sci;
    }

    /**
     * @param sci the sci to set
     */
    public void setSci(String sci) {
        this.sci = sci;
    }

    /**
     * @return the sec
     */
    public String getSec() {
        return sec;
    }

    /**
     * @param sec the sec to set
     */
    public void setSec(String sec) {
        this.sec = sec;
    }

    /**
     * @return the race
     */
    public String getRace() {
        return race;
    }

    /**
     * @param race the race to set
     */
    public void setRace(String race) {
        this.race = race;
    }

    /**
     * @return the traits
     */
    public String[] getTraits() {
        return traits;
    }

    /**
     * @param traits the traits to set
     */
    public void setTraits(String[] traits) {
        this.traits = traits;
    }

    /**
     * @return the inCollection
     */
    public boolean isInCollection() {
        return inCollection;
    }

    /**
     * @param inCollection the inCollection to set
     */
    public void setInCollection(boolean inCollection) {
        this.inCollection = inCollection;
    }

    /**
     * @return the starsFused
     */
    public String getStarsFused() {
        return starsFused;
    }

    /**
     * @param starsFused the starsFused to set
     */
    public void setStarsFused(String starsFused) {
        this.starsFused = starsFused;
    }

    /**
     * @return the level
     */
    public String getLevel() {
        return level;
    }

    /**
     * @param level the level to set
     */
    public void setLevel(String level) {
        this.level = level;
    }

    /**
     * @return the fullyEquiped
     */
    public boolean isFullyEquiped() {
        return fullyEquiped;
    }

    /**
     * @param fullyEquiped the fullyEquiped to set
     */
    public void setFullyEquiped(boolean fullyEquiped) {
        this.fullyEquiped = fullyEquiped;
    }

    /**
     * @return the inVault
     */
    public boolean isInVault() {
        return inVault;
    }

    /**
     * @param inVault the inVault to set
     */
    public void setInVault(boolean inVault) {
        this.inVault = inVault;
    }

    /**
     * @return the quantity
     */
    public String getQuantity() {
        return quantity;
    }

    /**
     * @param quantity the quantity to set
     */
    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

}
