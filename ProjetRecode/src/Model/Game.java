package Model;

import View.Window;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;


public class Game implements DeletableObserver {
	
    private ArrayList<GameObject> terrains = new ArrayList<GameObject>();//une ArrayList pour le terrain
    private ArrayList<Entity> entities = new ArrayList<Entity>();//Et une pour les entités, permet de dessiner les entités par dessus le sol.
    private ArrayList<Item> items = new ArrayList<Item>();

    private Window window;
    private int size;
    private int level = 0;

    public Game(Window window) throws IOException{
        this.window = window;
        
        //Creating the player
        
        Player player = new Player(this);
        player.attachDeletable(this);
        entities.add(player);
        window.setPlayer(player);
   
        new LevelUpdater(this);
    }
    
    
    public void startLevel() throws IOException {
    	if(level == 0 || (this.getAmountEntities() == 1 && this.entities.get(0).getPosY()==0)) {
    		terrains.clear();
    		items.clear();
    		level++;
    		
    		// Map building
            this.buildMap();
    		
    		try {
				this.getPlayer().setPosX(size/2);
				this.getPlayer().setPosY(size-2);
			} catch (Exception e) {
				e.printStackTrace();
			}

            addMonster(level);
            changeTextures();
            
            window.setGameObjects(terrains);//Une fois le terrain et toutes les entités dans les liste,
            window.setEntities(entities);//on ajoute les listes à la map et on rafraichit la map
            window.setItems(items);
    	}
    }
    
    private void changeTextures() {
    	for(GameObject elem : terrains) {
    		if(elem instanceof Hole) {
    			((Hole) elem).setSubID();
    		}
    	}
    }
    
    public void pickUpItem() {
    	boolean pickedUp = false;
    	for(Item item: items) {
    		try {
				if(item.isAtPosition(getPlayer().getPosX(), getPlayer().getPosY())) {
					getPlayer().addItem(item);
					pickedUp = true;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
    		if(pickedUp) {
    			item.activate();
    			break;
    		}
    	}
    	//TODO Réfléchir à déplacer dans le classe Player
    }

    /*
     * Vérifie si il y a un obstacle empêchant le déplacement d'une entité
     */
	public boolean checkObstacle(Direction direction, Entity entity) {
    	boolean obstacle = false;
    	int x = entity.getPosX();
		int y = entity.getPosY();
    	if(direction != null) {
    		x = entity.getPosX() + direction.getX();
        	y = entity.getPosY() + direction.getY();
    	}
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
	 * Fonction qui gère l'explosion d'une bombe et les dégats qu'elle inflige aux entités et à la carte
	 */
	
	public GameObject getBlockType(Direction side, GameObject entity) {
		int x = side.getX();
		int y = side.getY();
		for (GameObject object : terrains) {
            if (object.isAtPosition(entity.getPosX()+x, entity.getPosY()+y)) {
                return object;
            }
        }
    	for (GameObject object : entities) {
    		if (object.isAtPosition(entity.getPosX()+x, entity.getPosY()+y)) {
                return object;
            }
        }
    	return null;
	}
	
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
					if(object.isAtPosition(x+i, j+y) && object instanceof Damageable) {
						entity.add(object);
					}
				}
			}
		}
		for(GameObject object : terrain) {
			if(object instanceof BlockBreakable) {
				((Activable) object).activate();
			}
		}
		
		for(GameObject object : entity) {
			((Entity) object).sufferDamage(2);
		}
		//TODO Réfléchir à déplacer dans la classe ActveBomb
	}
	
	/*
	 * Fonction qui dépose une bombe active au coordonée souhaitées
	 */
	
	public void dropBomb(int x, int y) {
		ActiveBomb activeBomb = new ActiveBomb(x,y, this);
		activeBomb.attachDeletable(this);
		items.add(activeBomb);
	}
	

    public Window getWindow() {
    	return this.window;
    }

    /*
     *Supprime l'objet auquel est attaché le Deletable ps
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
    }
    
    /*
     * Fonction qui revoie l'objet joueur
     * (utilisé pour permettre au clavier de savoir quelle entité il dirige)
     */
    public Player getPlayer() throws Exception {
    	Entity player = entities.get(0);
    	if(player instanceof Player) {
    		return (Player) player;
    	}
    	else {
    		throw new Exception("No player");
    	}
    }
    
    /*
     * Fonction qui renvoie le nombre d'entité encore présente sur la carte.
     * (utilisé pour l'ouverture de la porte)
     */
    public int getAmountEntities() {
    	return entities.size();
    }
    
    public int getLevel() {
    	return level;
    }

    /*
     * Fonction qui se charge de créer les monstres et de les ajouters aléatoirement sur la carte
     * L'emplacement des monstres est vérifier avant de placer le monstre.
     */
    
    public void addMonster(int number) {
    	Random rand = new Random();
    	Entity monster;
    	
    	for(int i = 0;i<number;i++) {
    		TempEntity te = new TempEntity(0,0);
    		te.attachDeletable(this);
    		boolean obstacle = true;
    		
    		while(obstacle) {
    			te.setPosX(rand.nextInt(size-2) + 1);
    			te.setPosY(rand.nextInt(size-2) + 1);
    			obstacle = (checkObstacle(null,te) || playerInZone(te.getPosX(),te.getPosY(),5));
    		}
    		
    		int x = te.getPosX();
    		int y = te.getPosY();
    		te.notifyDeletableObserver();
    		int j = rand.nextInt(1);//Changer le 1 en le nombre de type de monstre que l'on fait appairaitre aléatoirement
    		if(j == 0) {
    			monster = new MonstreCaC(x, y, this);
    			monster.attachDeletable(this);
    			entities.add(monster);
    		}
    		else {
    			monster = new MonstreCaC(-1, -1, this);
    			monster.attachDeletable(this);
    			entities.add(monster);
    		}
    	}
    }
    
    
    /*
     * Fonction qui ajoute les block à la liste en fonction de leur type
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
		case 'H':
			terrains.add(new Hole(x,y, this));
			break;
		case 'D':
			terrains.add(new Door(x,y,this));
			break;
		case 'P':
			terrains.add(new Spike(x,y, this));
			break;
		case 'C':
			terrains.add(new Chest(x,y));
			break;
			
		default:
			//Créer un block par défaut
		}
    }
    
    public void throwProjectile(Entity entity) {
    	Projectile projectile = new Projectile(entity, 5,5,this);
    	projectile.attachDeletable(this);
    	entities.add(projectile);
    }
 
    /*
     * Fonction qui transforme la carte version texte en une liste de block
     */
    private void buildMap() throws IOException {
    	FileReader file = null;
        BufferedReader in = null;
        Random rand = new Random();
        int x = rand.nextInt(new File("maps").listFiles().length-1)+1;
        String map = "maps/map"+x+".txt";
        
        try {
        	file = new FileReader(map); //On ouvre le fichier de la map souhaité
        	in = new BufferedReader(file);//On met le fichier en mémoire
        	String line;
        	int lineNumber = 0;
        	while((line = in.readLine()) != null) {//On lit chaque ligne jusqu'à une ligne vide
        		for(int i = 0; i < line.length() ; i++) {
        			this.size = line.length();
        			char ID = line.charAt(i);//Le type de terrain est définit par le caractère utilisé dans le fichier texte
        			this.addObject(i, lineNumber, ID);//Ajoute le block à la liste de block du terrain
        		}
        		lineNumber++;
        	}
        	
        	
        } catch (Exception e) {
        	e.printStackTrace();
        } finally {
        	in.close();//On vide la mémoire du fichier texte
        	file.close();//On ferme le fichier
        }
    }

    /*
     * Fonction permettant de regarder si le joueur est dans la zone de vue d'un mnostre
     */
    
    public boolean playerInZone(int x, int y, int view) {
    	boolean isPlayerInZone = false;
    	try {
			if(this.getPlayer() != null) {
				for(int i = view*-1; i < view+1; i++) {
					for(int j = view*-1; j < view+1; j++) {
						if(this.getPlayer().isAtPosition(x+i, y+j)) {
							isPlayerInZone = true;
						}
						if(isPlayerInZone) {
							break;
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	return isPlayerInZone;
    	//TODO Réfléchir à déplacer hors du main (Peu probable au vu de l'utilisation de la fonction dans la génération des mobs
    }
}