package Domain.UserData;
import Domain.UserData.Interfaces.ISwimmer;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.ConcurrentHashMap;

public class Team {

    private String name;
    private LocalDateTime openDate;
    private String coachId;
    private ConcurrentHashMap<String, ISwimmer> swimmers;
    private AtomicInteger sendInvitations;
    private ConcurrentHashMap<String, Invitation> invitations;

    public Team(String name, String coachId) {
        this.name = name;
        this.coachId = coachId;
        this.openDate = LocalDateTime.now();
        this.swimmers = new ConcurrentHashMap<>();
        this.sendInvitations = new AtomicInteger(0);
        this.invitations = new ConcurrentHashMap<>();
    }

    public Team(
            String name,
            LocalDateTime openDate,
            String coachId,
            ConcurrentHashMap<String, ISwimmer> swimmers,
            int sendInvitations,
            List<Invitation> invitations) {
        this.name = name;
        this.openDate = openDate;
        this.coachId = coachId;
        this.swimmers = swimmers;
        this.sendInvitations = new AtomicInteger(sendInvitations);
        this.invitations = new ConcurrentHashMap<>();
        for (Invitation invitation : invitations) {
            this.invitations.putIfAbsent(invitation.getId(), invitation);
        }
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getOpenDate() {
        return openDate;
    }

    public String getCoachId() {
        return coachId;
    }

    public ConcurrentHashMap<String, ISwimmer> getSwimmers() {
        return swimmers;
    }

    public int getSendInvitations() {
        return sendInvitations.get();
    }

    public Collection<Invitation> getInvitations() {
        return invitations.values();
    }

    public Invitation addInvitation(ISwimmer swimmer) {
        String id = this.name + sendInvitations.getAndIncrement();
        Invitation invitation = new Invitation(id, this.name, swimmer.getEmail());
        if(this.invitations.putIfAbsent(id, invitation) == null) {
            if(swimmer.addInvitation(invitation)) {
                return invitation;
            }
            else {
                this.invitations.remove(id);
            }
        }
        return null;
    }

    public void deleteInvitation(Invitation invitation) {
        this.invitations.remove(invitation.getId());
    }


    public boolean addSwimmer(ISwimmer swimmer) {
        return swimmers.putIfAbsent(swimmer.getEmail(), swimmer) == null;
    }

    public boolean addSwimmer(ISwimmer swimmer, String invitationId) {
        return invitations.containsKey(invitationId)
                && swimmers.putIfAbsent(swimmer.getEmail(), swimmer) == null;
    }

    public boolean removeSwimmer(ISwimmer swimmer) {
        return swimmers.remove(swimmer.getEmail()) != null;
    }

    public void updateInvitation(Invitation invitation) {
        invitations.put(invitation.getId(), invitation);
    }

}
