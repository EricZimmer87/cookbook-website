export interface RoleDTO {
  roleId: number;
  roleName: string;
}

export interface UserDTO {
  userId: number;
  userName: string;
  userEmail: string;
  role: RoleDTO;
}
