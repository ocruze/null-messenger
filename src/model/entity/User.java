package model.entity;

public class User {

	private String username;
	private int idUser;
	
	public User(int idUser,String username) {
		this.idUser = idUser;
		this.username = username;
	}
	
	public String getUsername() {
		return username;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

	
	public int getIdUser() {
		return idUser;
	}

	@Override
	public String toString() {
		return username;
	}

}
