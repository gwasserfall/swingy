package models;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class EntityNames {
    public static final List<String> swords = Arrays.asList("Orcbane",
            "Devilish Dicer", "Bisectinator", "Careless Whisper",
            "Caladbolg", "Heartseeker", "Lifeender", "Diplomacy",
            "Judgment", "Finality", "The Unprettier", "Hushbringer",
            "Shining Malice", "Reaver", "Kingmaker", "Blood Drinker",
            "Talon", "Stonesplitter", "Dragonsbane", "Gutter",
            "Beheader", "Red Fang", "Lopper", "Gleaming Death",
            "Pain", "The Fury", "Bleeding Heart", "Cleaver",
            "Grinning Night", "Unhappy Ending", "Justice",
            "Blinding Truth", "Oblivion", "Heaven’s Grace", "Eternal Rest",
            "Sacrifice", "Finisher", "Lord’s Will", "Ice Finger",
            "Silence of the Dawn", "Iron Steed", "Sentinel",
            "Peace", "Rapture’s Gate", "Elysium", "Harmony",
            "Hereafter", "Tranquility", "Azure Dream", "Awakening",
            "Torment", "Gravedigger", "Abaddon", "Apocalypse",
            "Fate’s Last Dance", "Final Words", "Salt edge",
            "Stabwing", "Loki’s horn", "Xvartslayer", "Butterknife",
            "Icebane", "Magnum opus", "Blackan",
            "Black Scarlet", "Gutspiller", "Dirgesinger", "Innocence’ End",
            "Tyrant’s Demise", "Mr. Pointy", "Finality", "Gore",
            "Hell’s Afterbirth", "The Ending", "Herald of Mercy", "The Test",
            "Last Rites", "Gwendolyn", "The Dancing Edge", "Leg-Biter",
            "Deus vult", "Black Razor", "Crimson Wind", "Head Hunter",
            "Heart Seeker", "Scarlet Rain", "Shield Breaker", "Silence",
            "Soul Breaker", "Skull Splitter", "The Sun Sword", "Vengeance",
            "The Maw", "Curse Crusher", "Blood Spiller", "Author of Death",
            "Sloth’s Bane", "Backblade", "Callous Cleaver", "The Highroller");

    public static final List<String> helms = Arrays.asList("Jaws of Immortal Nightmares",
            "Greathelm of Hallowed Trials", "Bone Visage of Distant Hells",
            "Obsidian Helmet of Faded Trials", "Proud Ivory Casque",
            "Howling Ivory Crown", "Fearful Helmet of Inception",
            "Legionnaire's Casque of Limbo", "Hope of the Whispers",
            "Warden of the Dead", "Jaws of Divine Comrades",
            "Casque of Binding Sorrow", "Ivory Headguard of Hallowed Damnation",
            "Bronzed Helm of Endless Hells", "Soul-Forged Plate Helm",
            "Demonic Plate Jaws", "Fierce Crown of the Leviathan",
            "Hatred Helmet of the Moon", "Helmet of the Lasting Night",
            "Warden of the Talon", "Headpiece of Silent Nightmares",
            "Hood of Immortal Might", "Leather Coif of Infernal Fires",
            "Hide Facemask of Frozen Fortunes", "Lich Padded Helm",
            "Mourning Heavy Hide Helm", "Frost Headguard of Hellfire",
            "Crazed Cap of Runes", "Whisper of Lifestealing",
            "Promise of Lifemending", "Bandana of Binding Comrades",
            "Cowl of Infernal Vengeance", "Quilted Mask of Fleeting Visions",
            "Heavy Hide Headpiece of Haunted Whispers", "Infused Padded Mask",
            "Military Rugged Leather Bandana", "Bloodied Helm of Devotion",
            "Frost Hood of Ashes", "Bandana of Delusions",
            "Demise of Shifting Sands");

    public static final List<String> armor = Arrays.asList("Greatplate of Broken Honor",
            "Breastplate of Burning Comrades", "Chainmail Chestguard of Infernal Nights",
            "Adamantite Cuirass of Twisted Illusions", "Remorse Iron Chestpiece",
            "Forsaken Titanium Batteplate", "Remorse Batteplate of the Whispers",
            "Storm-Forged Chestpiece of Broken Dreams", "Defense of the Isles",
            "Chestpiece of Ended Dreams", "Tunic of Eternal Sorrow",
            "Batteplate of Relentless Power", "Bronzed Chestplate of Demonic Misery",
            "Titanium Chestpiece of Demonic Visions", "Haunted Mithril Armor",
            "Undead Mail Breastplate", "Yearning Chestpiece of the Lion",
            "Wrathful Armor of Necromancy", "Greatplate of the Lone Wolf",
            "Foe of Regret", "Raiment of Infernal Freedom", "Jerkin of Eternal Magic",
            "Linen Jerkin of Divine Trials", "Leather Raiment of Divine Vengeance",
            "Quilted Breastplate", "War-Forged Heavy Leather Garments",
            "Thunder Raiment of Absorption", "Challenger Robes of Fire Resist",
            "Fall of Faded Memories", "Dawn of Thunders", "Vest of Fallen Trials",
            "Chestguard of Silent Worlds", "Scaled Vestment of Unholy Worlds",
            "Padded Jerkin of Divine Trials", "Victor Silk Robes",
            "Dire Quilted Jerkin", "Woeful Garments of Hellish Torment",
            "Woeful Wraps of the Lost", "Guardian of Honor", "Birth of the West");

    public static String selectRandomPowerUpName(String typeOf) {
        Random random = new Random();

        if (typeOf.equalsIgnoreCase("weapon")) {
            return swords.get(random.nextInt(swords.size()));
        }
        else if (typeOf.equalsIgnoreCase("helm")) {
            return helms.get(random.nextInt(helms.size()));
        }
        else if (typeOf.equalsIgnoreCase("armor")) {
            return armor.get(random.nextInt(armor.size()));
        }
        return "The unnamed";
    }
}
