package Domain.UserData;
import Domain.UserData.Interfaces.IInvitation;
import Domain.UserData.Interfaces.ISwimmer;
import Domain.UserData.Interfaces.ITeam;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.ConcurrentHashMap;

public class Team implements ITeam {

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

    @Override
    public String getName() {
        return name;
    }

    @Override
    public LocalDateTime getOpenDate() {
        return openDate;
    }

    @Override
    public String getCoachId() {
        return coachId;
    }

    @Override
    public Collection<? extends ISwimmer> getSwimmersCollection() {
        return this.swimmers.values();
    }

    @Override
    public Collection<? extends IInvitation> getInvitationsCollection() {
        return this.invitations.values();
    }

    @Override
    public boolean hasSwimmer(ISwimmer swimmer) {
        return this.swimmers.containsKey(swimmer.getEmail());
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

    public boolean addSwimmer(ISwimmer swimmer, String invitationId, Invitation updated) {
        return invitations.containsKey(invitationId)
                && invitations.put(invitationId, updated) != null
                && swimmers.putIfAbsent(swimmer.getEmail(), swimmer) == null;
    }

    public boolean removeSwimmer(ISwimmer swimmer) {
        return swimmers.remove(swimmer.getEmail()) != null;
    }

    public void updateInvitation(Invitation invitation) {
        invitations.put(invitation.getId(), invitation);
    }

}
