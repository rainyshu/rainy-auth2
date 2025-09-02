
import { request } from '@/utils'

export default {
  create: data => request.post('/role/add', data),
  read: (params = {}) => request.get('/role/getPage', { params }),
  update: data => request.patch(`/role/update`, data),
  delete: id => request.delete(`/role/${id}`),

  getAllPermissionTree: () => request.get('/permission/tree'),
  getAllUsers: (params = {}) => request.get('/user', { params }),
  addRoleUsers: (roleId, data) => request.patch(`/role/users/add/${roleId}`, data),
  removeRoleUsers: (roleId, data) => request.patch(`/role/users/remove/${roleId}`, data),
}
