class Field:
    def __init__(self, step: float, cell_size: float, x_spot: int, y_spot: int, z_spot: float):
        ''' step - расстояние между центрами соседних ячеек (не по диагонали)
        cell_size - размер ячейки (сторона квадрата или диаметр круга)
        x_spot, y_spot - координаты источника света в условных единицах (не см, клетки поля)
        z_spot - координата высоты источника света над полем (в см)
        '''
        self.step = step
        self.cell_size = cell_size
        self.x_spot = x_spot
        self.y_spot = y_spot
        self.z_spot = z_spot

    def find_projection(self, fig):
        actual_fig_x = fig.x * self.step
        actual_fig_y = fig.y * self.step

        fig_top_x = actual_fig_x + fig.x_top
        fig_top_y = actual_fig_y + fig.y_top
        fig_top_z = fig.z_top  # 0 + fig.z_top

        t = ((self.x_spot - fig_top_x) ** 2 +
             (self.y_spot - fig_top_y) ** 2 +
             (self.z_spot - fig_top_z) ** 2) ** 0.5

        l = (fig_top_x - self.x_spot) / t
        m = (fig_top_y - self.y_spot) / t
        n = (fig_top_z - self.z_spot) / t

        x_proj = -self.z_spot * l / n
        y_proj = -self.z_spot * m / n

        return self.x_spot + x_proj, self.y_spot + y_proj
    
    
    def find_dot_projection(self, fig_top_x,fig_top_y,fig_top_z):

        t = ((self.x_spot - fig_top_x) ** 2 +
             (self.y_spot - fig_top_y) ** 2 +
             (self.z_spot - fig_top_z) ** 2) ** 0.5

        l = (fig_top_x - self.x_spot) / t
        m = (fig_top_y - self.y_spot) / t
        n = (fig_top_z - self.z_spot) / t

        x_proj = -self.z_spot * l / n
        y_proj = -self.z_spot * m / n

        return self.x_spot + x_proj, self.y_spot + y_proj

    def is_proj_inside_cell(self, fig):
        x_proj, y_proj = self.find_projection(fig)

        actual_fig_x = fig.x * self.step
        actual_fig_y = fig.y * self.step

        return fig.is_dot_isnide_cell(actual_fig_x, actual_fig_y, x_proj, y_proj, self.cell_size)
