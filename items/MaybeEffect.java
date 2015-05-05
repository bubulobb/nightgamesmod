package items;

import global.Global;
import combat.Combat;
import characters.Character;
import characters.body.Body;
import characters.body.TentaclePart;
import status.Status;

public class MaybeEffect extends ItemEffect {
	private ItemEffect effect;
	private double probability;
	public MaybeEffect(ItemEffect other, double probability) {
		super("","",true, true);
		this.probability = probability;
		this.effect = other;
	}
	public boolean use(Combat c, Character user, Character opponent, Item item) {
		if (Global.randomdouble() < probability) {
			return effect.use(c, user, opponent, item);
		}
		return false;
	}
}