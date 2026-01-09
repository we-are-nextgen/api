package org.nextgen.dto.bootcamp;

public class TeamMemberDTO {
    public String email;
    public String bio;
    public String fullName;
    public String headline;
    public String skills;

    public static TeamMemberDTO tDto(String email, String bio, String fullName, String headline, String skills){
        TeamMemberDTO dto = new TeamMemberDTO();
        dto.email = email;
        dto.bio = bio;
        dto.fullName = fullName;
        dto.headline = headline;
        dto.skills = skills;
        return dto;
    }
}
