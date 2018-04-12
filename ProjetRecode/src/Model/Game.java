package Model;

import View.Window;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;


public class Game implements DeletableObserver {
	
    private ArrayList<GameObject> terrains = new ArrayList<GameObject>();//une ArrayList pour le terrain
    private ArrayList<Entity> entities = new ArrayList<Entity>();//Et une pour les entit�s, permet de dessiner les entit�s par dessus le sol.
    private ArrayList<Item> items = new ArrayList<Item>();
    
    /*
     * Pour faire les listes de map,
     * Prendre un param�tre de taille de map
     * avec celui-ci aller chercher l'ensemble des maps de cette taille
     * celles-ci se trouveraient dans un dossier du nom de leur taille
     * Utiliser les proposition trouv�e ici https://stackoverflow.com/questions/5694385/getting-the-filenames-of-all-files-in-a-folder
     * Ensuite faire un random sur la longeur de la liste obtenue et prendre la map qui correspond au random
     * 
     */

    private Window window;
    private int size;
    private int lineNumber;
    private int level = 0;

    public Game(Window window) throws IOException{
        this.window = window;
        size = window.getSize();
        
        //Creating the player
        
        Player player = new Player(size/2, size/2, 3, 5);
        player.attachDeletable(this);
        entities.add(player);
        window.setPlayer(player);
   
        startLevel();
        notifyView();
    }
    
    private void startLevel() throws IOException {
    	if(level == 0 || (this.getAmountEntities() == 1 && this.entities.get(0).isAtPosition(15, 0))) {
    		terrains.clear();
    		items.clear();
    		level++;
    		
    		this.getPlayer().setPosX(15);
    		this.getPlayer().setPosY(28);

            // Map building
            this.buildMap();
            
            addMonster(level);
            
            window.setGameObjects(terrains);//Une fois le terrain et toutes les entit�s dans les liste,
            window.setEntities(entities);//on ajoute les listes � la map et on rafraichit la map
            window.setItems(items);
    	}
    }
    
    /*
     * Fonction qui v�rifie les condition n�c�ssaire au d�placement d'une entit�
     */
    public void moveEntity(int x, int y, Entity entity) {
    	int nextX = entity.getPosX() + x;
        int nextY = entity.getPosY() + y;

        if (!checkObstacle(nextX, nextY, entity)) {
            entity.move(x, y);
        }
        notifyView();
    }

    /*
     * V�rifie si il y a un obstacle emp�chant le d�placement d'une entit�
     */
	public boolean checkObstacle(int x, int y, Entity entity) {
    	boolean obstacle = false;
    	for (GameObject object : terrains) {
            if (object.isAtPosition(x, y)) {//regarde si il y a un obstacle devant
                obstacle = object.isObstacle(entity);
            }
            if(obstacle == true) {
            	break;
            }
        }
    	for (GameObject object : entities) {
    		if(obstacle == true) {
            	break;
            }
            if (object.isAtPosition(x, y)) {//regarde si il y a un obstacle devant
                obstacle = object.isObstacle(entity);
            }
        }
    	for (GameObject object : items) {
    		if(obstacle == true) {
            	break;
            }
            if (object.isAtPosition(x, y)) {//regarde si il y a un obstacle devant
                obstacle = object.isObstacle(entity);
            }
        }
    	return obstacle;
    }
	
	/*
	 * Fonction qui g�re l'explosion d'une bombe et les d�gats qu'elle inflige aux entit�s et � la carte
	 */
	
	public void explode(ActiveBomb bomb) {
		int x = bomb.getPosX();
		int y = bomb.getPosY();
		ArrayList<GameObject> terrain = new ArrayList<GameObject>();
		ArrayList<GameObject> entity = new ArrayList<GameObject>();
		for(int i = -1;i<2;i++) {
			for(int j = -1; j < 2;j++) {
				for(GameObject object : terrains) {
					if(object.isAtPosition(x+i, j+y) && object instanceof Activable) {
						terrain.add(object);
					}
				}
				for(GameObject object : entities) {
					if(object.isAtPosition(x+i, j+y) && object instanceof Activable) {
						entity.add(object);
					}
				}
			}
		}
		for(GameObject object : terrain) {
			((BlockBreakable) object).activate();
		}
		for(GameObject object : entity) {
			((Entity) object).activate();
		}
	}
	
	/*
	 * Fonction qui d�pose une bombe active au coordon�e souhait�es
	 */
	
	public void dropBomb(int x, int y) {
		ActiveBomb activeBomb = new ActiveBomb(x,y, this);
		activeBomb.attachDeletable(this);
		items.add(activeBomb);
	}
    
	/*
	 * Fonction qui v�rifie si il y a quelque chose � attaquer et, le cas �ch�ant, attaque
	 */
    public void attack(Direction direction, Entity attacker) {
    	int x = attacker.getPosX();
    	int y = attacker.getPosY();
    	switch(direction) {
    	case Left :
    		x--;
    		attacker.setDirection(Direction.Left);
    		break;
    	case Right:
    		x++;
    		attacker.setDirection(Direction.Right);
    		break;
    	case Up:
    		y--;
    		attacker.setDirection(Direction.Up);
    		break;
    	case Down:
    		y++;
    		attacker.setDirection(Direction.Down);
    		break;
    	}
    	Activable aimedObject = null;
		for(GameObject object : entities){
			if(object.isAtPosition(x,y)){
			    if(object instanceof Activable){
			        aimedObject = (Activable) object;
			    }
			}
		}
		for(GameObject object : items) {
			if(object.isAtPosition(x,y)){
			    if(object instanceof Bomb){
			        ((Player) attacker).addItem((Item) object); 
			    }
			    aimedObject = (Activable) object;
			}
		}
		if(aimedObject != null){
		    aimedObject.activate();
		}
		notifyView();
    }

    /*
     * Met l'affichage � jour
     */
    private void notifyView() {
    	try {
			startLevel();
		} catch (IOException e) {
			e.printStackTrace();
		}
        window.update();
        window.updateInventory();
    }

    /*
     *Supprime l'objet auquel est attach� le Deletable ps
     */
	@Override
    synchronized public void delete(Deletable ps, Item item) {
        entities.remove(ps);
        terrains.remove(ps);
        items.remove(ps);
        if (item != null) {
        	item.attachDeletable(this);
            items.add(item);
        }
        notifyView();
    }

	/*
	 * Affiche la position du joueur dans la console
	 */
    public void playerPos(int playerNumber) {
        Player player = ((Player) entities.get(playerNumber));
        System.out.println(player.getPosX() + ":" + player.getPosY());
    }
    
    /*
     * Fonction qui revoie l'objet joueur
     * (utilis� pour permettre au clavier de savoir quelle entit� il dirige)
     */
    public GameObject getPlayer() {
    	return entities.get(0);
    }
    
    /*
     * Fonction qui renvoie le nombre d'entit� encore pr�sente sur la carte.
     * (utilis� pour l'ouverture de la porte)
     */
    public int getAmountEntities() {
    	return entities.size();
    }
    
    public int getLevel() {
    	return level;
    }

    /*
     * Fonction qui se charge de cr�er les monstres et de les ajouters al�atoirement sur la carte
     * L'emplacement des monstres est v�rifier avant de placer le monstre.
     */
    
    public void addMonster(int number) {
    	Random rand = new Random();
    	Entity monster;
    	int x = -1;
    	int y = -1;
    	for(int i = 0;i<number;i++) {
    		int j = rand.nextInt(1);//Changer le 1 en le nombre de type de monstre que l'on fait appairaitre al�atoirement
    		if(j == 0) {
    			monster = new MonstreCaC(x, y, window, this);
    			monster.attachDeletable(this);
    			entities.add(monster);
    		}
    		else {
    			monster = new MonstreCaC(-1, -1, window, this);
    			monster.attachDeletable(this);
    			entities.add(monster);
    		}
    		
    		boolean obstacle = true;
    		while(obstacle) {
    			x = rand.nextInt(28) + 1;
    			y = rand.nextInt(28) + 1;
    			obstacle = checkObstacle(x,y,null);
    		}
    		monster.setPosX(x);
    		monster.setPosY(y);
    	}
    }
    
    
    /*
     * Fonction qui ajoute les block � la liste en fonction de leur type
     * En cas de block cassable, ne pas oublier d'attacher un Deletable pour pouvoir le supprimer.
     */
    private void addObject(int x, int y, char ID) {
    	switch(ID){
		case 'A':
			terrains.add(new BlockUnbreakable(x, y));
			break;
		case 'B':
			BlockBreakable block = new BlockBreakable(x, y);
            block.attachDeletable(this);
            terrains.add(block);
			break;
		case 'C':
			terrains.add(new BlockWater(x, y));
			break;
		case 'H':
			terrains.add(new Hole(x,y));
			break;
		case 'D':
			terrains.add(new Door(x,y,this));
			break;
		case 'P':
			terrains.add(new Spike(x,y,(Player) entities.get(0), this));
			break;
			
		default:
			//Cr�er un block par d�faut
		}
    }
 
    /*
     * Fonction qui transforme la carte version texte en une liste de block
     */
    private void buildMap() throws IOException {
    	FileReader file = null;
        BufferedReader in = null;
        Random rand = new Random();
        int x = rand.nextInt(2);
        String map;
        if(x == 0) {
        	map = "maps/map2.txt";
        }
        else {
        	map = "maps/map3.txt";
        }
        
        try {
        	file = new FileReader(map); //On ouvre le fichier de la map souhait�
        	in = new BufferedReader(file);//On met le fichier en m�moire
        	String line;
        	lineNumber = 0;
        	while((line = in.readLine()) != null) {//On lit chaque ligne jusqu'� une ligne vide
        		for(int i = 0; i < size ; i++) {
        			char ID = line.charAt(i);//Le type de terrain est d�finit par le caract�re utilis� dans le fichier texte
        			this.addObject(i, lineNumber, ID);//Ajoute le block � la liste de block du terrain
        		}
        		lineNumber++;
        	}
        	
        	
        } catch (Exception e) {
        	e.printStackTrace();
        } finally {
        	in.close();//On vide la m�moire du fichier texte
        	file.close();//On ferme le fichier
        }
    }

    /*
     * Fonction permettant de regarder si le joueur est dans la zone de vue d'un mnostre
     */
    
    public boolean playerInZone(int x, int y, int view) {
    	boolean isPlayerInZone = false;
    	for(int i = view*-1; i < view+1; i++) {
    		for(int j = view*-1; j < view+1; j++) {
    			if(entities.get(0).isAtPosition(x+i, y+j)) {
    				isPlayerInZone = true;
    			}
    			if(isPlayerInZone) {
    				break;
    			}
    		}
    		
    	}
    	return isPlayerInZone;
    }
}