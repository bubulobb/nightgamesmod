package nightgames.skills;

import nightgames.characters.Character;
import nightgames.characters.Emotion;
import nightgames.characters.Trait;
import nightgames.combat.Combat;
import nightgames.combat.Result;

public class Bravado extends Skill {
    int cost;

    public Bravado(Character self) {
        super("Determination", self);
        cost = 0;
    }

    @Override
    public boolean requirements(Combat c, Character user, Character target) {
        return user.has(Trait.fearless);
    }

    @Override
    public boolean usable(Combat c, Character target) {
        return getSelf().canRespond() && c.getStance().mobile(getSelf());
    }

    @Override
    public int getMojoCost(Combat c) {
        cost = Math.max(20, getSelf().getMojo().get());
        return cost;
    }

    @Override
    public boolean resolve(Combat c, Character target) {
        int x = cost;
        if (getSelf().human()) {
            c.write(getSelf(), deal(c, x, Result.normal, target));
        } else if (target.human()) {
            c.write(getSelf(), receive(c, x, Result.normal, target));
        }
        getSelf().calm(c, 20 + x / 2);
        getSelf().heal(c, x);
        getSelf().restoreWillpower(c, 2 + x / 10);
        getSelf().emote(Emotion.confident, 30);
        getSelf().emote(Emotion.dominant, 20);
        getSelf().emote(Emotion.nervous, -20);
        getSelf().emote(Emotion.desperate, -30);
        return true;
    }

    @Override
    public Skill copy(Character user) {
        return new Bravado(user);
    }

    @Override
    public Tactics type(Combat c) {
        return Tactics.recovery;
    }

    @Override
    public String deal(Combat c, int damage, Result modifier, Character target) {
        return "You grit your teeth and put all your willpower into the fight.";
    }

    @Override
    public String receive(Combat c, int damage, Result modifier, Character attacker) {
        return getSelf().name() + " gives you a determined glare as she seems to gain a second wind.";
    }

    @Override
    public String describe(Combat c) {
        return "Consume mojo to restore stamina and reduce arousal";
    }

}
